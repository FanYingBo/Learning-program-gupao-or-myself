# Dubbo 启动方式—Spring

Dubbo采用Spring Schema 来加载和初始化Dubbo的配置信息，通过Spring事件发布机制来加载，启动，暴露服务，注册服务

## Spring Schema 加载机制 

Spring 在解析 xml 配置文件之前会先加载， META-INF下的`spring.handlers` 和`spring.schemas`，然后通过 xml 中的地址找到 schema，通过schame的地址匹配对应的 （`spring.handlers`）中的NameSpaceHandle

## Dubbo加载过程

1. Dubbo通过xml中配置 的Schema 地址`http://code.alibabatech.com/schema/dubbo`匹配到 `DubboNamespaceHandler`

2. Spring 在解析xml配置时会通过执行 `DubboNamespaceHandler` 的 `init`方法来注册Double配置所需要的的`DubboBeanDefinitionParser`

3. 

   ```java
   public class DubboNamespaceHandler extends NamespaceHandlerSupport implements ConfigurableSourceBeanMetadataElement {
   
       static {
           Version.checkDuplicate(DubboNamespaceHandler.class);
       }
   
       @Override
       public void init() {
           // 注册BeanDefinitionParser
           // 这里的Config对应xml 中的<dubbo:application >
           registerBeanDefinitionParser("application", new DubboBeanDefinitionParser(ApplicationConfig.class, true));
           
           registerBeanDefinitionParser("module", new DubboBeanDefinitionParser(ModuleConfig.class, true));
           // 这里的Config对应xml 中的<dubbo:registry >
           registerBeanDefinitionParser("registry", new DubboBeanDefinitionParser(RegistryConfig.class, true));
           registerBeanDefinitionParser("config-center", new DubboBeanDefinitionParser(ConfigCenterBean.class, true));
           
           registerBeanDefinitionParser("metadata-report", new DubboBeanDefinitionParser(MetadataReportConfig.class, true));
           
           registerBeanDefinitionParser("monitor", new DubboBeanDefinitionParser(MonitorConfig.class, true));
           
           registerBeanDefinitionParser("metrics", new DubboBeanDefinitionParser(MetricsConfig.class, true));
           registerBeanDefinitionParser("ssl", new DubboBeanDefinitionParser(SslConfig.class, true));
           
           registerBeanDefinitionParser("provider", new DubboBeanDefinitionParser(ProviderConfig.class, true));
           registerBeanDefinitionParser("consumer", new DubboBeanDefinitionParser(ConsumerConfig.class, true));
           registerBeanDefinitionParser("protocol", new DubboBeanDefinitionParser(ProtocolConfig.class, true));
           registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
           registerBeanDefinitionParser("reference", new DubboBeanDefinitionParser(ReferenceBean.class, false));
           registerBeanDefinitionParser("annotation", new AnnotationBeanDefinitionParser());
       }
    // ... ...
       /**
        * Override {@link NamespaceHandlerSupport#parse(Element, ParserContext)} method
        *
        * @param element       {@link Element}
        * @param parserContext {@link ParserContext}
        * @return	
        * @since 2.7.5
        */
       @Override
       public BeanDefinition parse(Element element, ParserContext parserContext) { // BeanDefinitionParserDelegate#parseCustomElement 中调用该方法
           BeanDefinitionRegistry registry = parserContext.getRegistry();
           registerAnnotationConfigProcessors(registry);
           registerApplicationListeners(registry);
           BeanDefinition beanDefinition = super.parse(element, parserContext); //这里调用父类NamespaceHandlerSupport的parse(Element,ParserContext)
           setSource(beanDefinition);
           return beanDefinition;
       }
   }
   ```

   ```java
   public abstract class NamespaceHandlerSupport implements NamespaceHandler {
       /**
   	 * Stores the {@link BeanDefinitionParser} implementations keyed by the
   	 * local name of the {@link Element Elements} they handle.
   	 */
   	private final Map<String, BeanDefinitionParser> parsers =
   			new HashMap<String, BeanDefinitionParser>(); // 定义的BeanDefinitionParser Dubbo里面是DubboBeanDefinitionParser
   
   	/**
   	 * Stores the {@link BeanDefinitionDecorator} implementations keyed by the
   	 * local name of the {@link Element Elements} they handle.
   	 */
   	private final Map<String, BeanDefinitionDecorator> decorators =
   			new HashMap<String, BeanDefinitionDecorator>();
   
   	/**
   	 * Stores the {@link BeanDefinitionDecorator} implementations keyed by the local
   	 * name of the {@link Attr Attrs} they handle.
   	 */
   	private final Map<String, BeanDefinitionDecorator> attributeDecorators =
   			new HashMap<String, BeanDefinitionDecorator>();
   
   
   	@Override
   	public BeanDefinition parse(Element element, ParserContext parserContext) {
   		return findParserForElement(element, parserContext).parse(element, parserContext);
   	}
   
   	/**
   	 * Locates the {@link BeanDefinitionParser} from the register implementations using
   	 * the local name of the supplied {@link Element}.
   	 */
   	private BeanDefinitionParser findParserForElement(Element element, ParserContext parserContext) {
   		String localName = parserContext.getDelegate().getLocalName(element);
   		BeanDefinitionParser parser = this.parsers.get(localName); // 通过标签的名称获取BeanDefinitionParser
   		if (parser == null) {
   			parserContext.getReaderContext().fatal(
   					"Cannot locate BeanDefinitionParser for element [" + localName + "]", element);
   		}
   		return parser;
   	}
   }
   ```

   

   ```java
   public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {
   
   	/**
   	 * The location to look for the mapping files. Can be present in multiple JAR files.
   	 */
   	public static final String DEFAULT_HANDLER_MAPPINGS_LOCATION = "META-INF/spring.handlers";
   
   
   	/** Logger available to subclasses */
   	protected final Log logger = LogFactory.getLog(getClass());
   
   	/** ClassLoader to use for NamespaceHandler classes */
   	private final ClassLoader classLoader;
   
   	/** Resource location to search for */
   	private final String handlerMappingsLocation;
   
   	/** Stores the mappings from namespace URI to NamespaceHandler class name / instance */
   	private volatile Map<String, Object> handlerMappings; //映射到对应的NamespaceHandler
   	// ... ... 
       
       /**
   	 * Locate the {@link NamespaceHandler} for the supplied namespace URI
   	 * from the configured mappings.
   	 * @param namespaceUri the relevant namespace URI
   	 * @return the located {@link NamespaceHandler}, or {@code null} if none found
   	 */
   	@Override
   	public NamespaceHandler resolve(String namespaceUri) {
   		Map<String, Object> handlerMappings = getHandlerMappings();
   		Object handlerOrClassName = handlerMappings.get(namespaceUri);
   		if (handlerOrClassName == null) {
   			return null;
   		}
   		else if (handlerOrClassName instanceof NamespaceHandler) {
   			return (NamespaceHandler) handlerOrClassName;
   		}
   		else {
   			String className = (String) handlerOrClassName;
   			try {
   				Class<?> handlerClass = ClassUtils.forName(className, this.classLoader);
   				if (!NamespaceHandler.class.isAssignableFrom(handlerClass)) {
   					throw new FatalBeanException("Class [" + className + "] for namespace [" + namespaceUri +
   							"] does not implement the [" + NamespaceHandler.class.getName() + "] interface");
   				}
   				NamespaceHandler namespaceHandler = (NamespaceHandler) BeanUtils.instantiateClass(handlerClass);
   				namespaceHandler.init(); // 初始化NamespaceHandler
   				handlerMappings.put(namespaceUri, namespaceHandler);
   				return namespaceHandler;
   			}
   			catch (ClassNotFoundException ex) {
   				throw new FatalBeanException("NamespaceHandler class [" + className + "] for namespace [" +
   						namespaceUri + "] not found", ex);
   			}
   			catch (LinkageError err) {
   				throw new FatalBeanException("Invalid NamespaceHandler class [" + className + "] for namespace [" +
   						namespaceUri + "]: problem with handler class file or dependent class", err);
   			}
   		}
   	}
   }
   ```

   ```java
   public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {
       
       //... ...
   	/**
   	 * Parse the elements at the root level in the document:
   	 * "import", "alias", "bean".
   	 * @param root the DOM root element of the document
   	 */
   	protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
   		if (delegate.isDefaultNamespace(root)) {
   			NodeList nl = root.getChildNodes();
   			for (int i = 0; i < nl.getLength(); i++) {
   				Node node = nl.item(i);
   				if (node instanceof Element) {
   					Element ele = (Element) node;
   					if (delegate.isDefaultNamespace(ele)) {
   						parseDefaultElement(ele, delegate);
   					}
   					else {
   						delegate.parseCustomElement(ele); //BeanDefinitionParserDelegate#parseCustomElement
   					}
   				}
   			}
   		}
   		else {
   			delegate.parseCustomElement(root);
   		}
   	}
   
   	private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
   		if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {
   			importBeanDefinitionResource(ele);
   		}
   		else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) {
   			processAliasRegistration(ele);
   		}
   		else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) {
   			processBeanDefinition(ele, delegate);
   		}
   		else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)) {
   			// recurse
   			doRegisterBeanDefinitions(ele);
   		}
   	}
       // ... ... 
   }
   ```

   

   ```java
   public class BeanDefinitionParserDelegate {
   	// ... ...
   
   	public BeanDefinition parseCustomElement(Element ele) {
   		return parseCustomElement(ele, null);
   	}
   
   	public BeanDefinition parseCustomElement(Element ele, BeanDefinition containingBd) {
   		String namespaceUri = getNamespaceURI(ele);
   		NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
   		if (handler == null) {
   			error("Unable to locate Spring NamespaceHandler for XML schema namespace [" + namespaceUri + "]", ele);
   			return null;
   		}
   		return handler.parse(ele, new ParserContext(this.readerContext, this, containingBd)); // 调用DubboNamespaceHandler#parser()
   	}
   // ... ...
   }
   ```

   

# 备注

可参考dubbo官方文档：http://dubbo.apache.org/zh-cn/docs/user/quick-start.html

