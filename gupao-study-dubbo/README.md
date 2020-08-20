# Dubbo 启动方式

Dubbo采用Spring Schema 来加载和初始化Dubbo的配置信息，通过Spring事件监听发布机制来加载，启动，发布和注册服务

## Spring Schema 加载机制 

Spring 在解析 xml 配置文件之前会先加载， META-INF下的`spring.handlers` 和`spring.schemas`，然后通过 xml 中的地址找到 schema，通过schame的地址匹配对应的 （`spring.handlers`）中的NameSpaceHandle

## Dubbo SPI

1. Dubbo SPI

## Dubbo配置加载过程

1. Dubbo通过xml中配置 的Schema 地址`http://code.alibabatech.com/schema/dubbo`匹配到 `DubboNamespaceHandler`

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

   

2. Spring 在解析xml配置时会通过执行 `DubboNamespaceHandler` 的 `init`方法来注册Double配置所需要的的`DubboBeanDefinitionParser`

3. Spring在解析xml节点信息是会调`NamespaceHandlerSupport#parse` ,即已经注册的`DubboBeanDefinitionParser#parse`方法

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
   		return handler.parse(ele, new ParserContext(this.readerContext, this, containingBd)); // 调用DubboNamespaceHandler#parse()
   	}
   // ... ...
   }
   ```

   4. 在调用`DubboNamespaceHandler#parser()`方法的时候会注册Dubbo的启动监听程序，Dubbo的监听程序是依赖于Spring 的 ApplicationEventListener，Spring 在refresh()  启动时会通过调用`org.springframework.context.ApplicationListener#onApplicationEvent(E event)`发布ApplicationContextEvent 事件，`org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener`通过监听 `ContextRefreshedEvent` 事件和 `ContextClosedEvent` 事件来控制Dubbo服务的生命周期

      ```java
       public class DubboNamespaceHandler extends NamespaceHandlerSupport implements ConfigurableSourceBeanMetadataElement {
       	//... ...
           // 继承自DubboNamespaceHandler#parse()
           @Override
          public BeanDefinition parse(Element element, ParserContext parserContext) {
              BeanDefinitionRegistry registry = parserContext.getRegistry();
              registerAnnotationConfigProcessors(registry);
              registerApplicationListeners(registry);
              BeanDefinition beanDefinition = super.parse(element, parserContext);
              setSource(beanDefinition);
              return beanDefinition;
          }
           
            /**
           * Register {@link ApplicationListener ApplicationListeners} as a Spring Bean
           *
     * @param registry {@link BeanDefinitionRegistry}
           * @see ApplicationListener
     * @see AnnotatedBeanDefinitionRegistryUtils#registerBeans(BeanDefinitionRegistry, Class[])
           * @since 2.7.5
           */
          private void registerApplicationListeners(BeanDefinitionRegistry registry) {
              registerBeans(registry, DubboLifecycleComponentApplicationListener.class);
              registerBeans(registry, DubboBootstrapApplicationListener.class); // 启动引导程序应用监听
          }
          // ... ...
      }
      ```
      
      ```java
      public class DubboBootstrapApplicationListener extends OneTimeExecutionApplicationContextEventListener
              implements Ordered {
          /**
           * The bean name of {@link DubboBootstrapApplicationListener}
           *
           * @since 2.7.6
           */
          public static final String BEAN_NAME = "dubboBootstrapApplicationListener";
      
          private final DubboBootstrap dubboBootstrap;
      
          public DubboBootstrapApplicationListener() {
              this.dubboBootstrap = DubboBootstrap.getInstance();
          }
          
          @Override
          public void onApplicationContextEvent(ApplicationContextEvent event) {
              if (event instanceof ContextRefreshedEvent) {
                  onContextRefreshedEvent((ContextRefreshedEvent) event);
              } else if (event instanceof ContextClosedEvent) {
                  onContextClosedEvent((ContextClosedEvent) event);
        }
          }
   
          private void onContextRefreshedEvent(ContextRefreshedEvent event) {
           dubboBootstrap.start(); // 启动Dubbo服务
          }
   
          private void onContextClosedEvent(ContextClosedEvent event) {
           dubboBootstrap.stop(); // 停止Dubbo服务
          }
   }
      ```
      
      
      
   5. 在Dubbo启动前，由于所有的Dubbo 的每个 ServiceBean 配置都继承了`org.apache.dubbo.config.AbstractConfig`并同时继承了  ApplicationContextAware, BeanNameAware, ApplicationEventPublisherAware ，在初始化配置相关的Bean时会执行`@PostConstruct`修饰的方法（Dubbo2.7.5）addIntoConfigManager()添加到`org.apache.dubbo.config.context.ConfigManager`中的configsCache，这个配置会在Dubbo启动发布注册服务时候会获取该配置信息，这时SpringBean 与Dubbo 的 ServiceBean 配置就可以关联起来。
   
      ```java
      public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean,
              ApplicationContextAware, BeanNameAware, ApplicationEventPublisherAware {
      
      	@Override
          public void setApplicationContext(ApplicationContext applicationContext) {
              this.applicationContext = applicationContext;
              SpringExtensionFactory.addApplicationContext(applicationContext); // 添加Spring应用的上下文，可以通过上下文获取SpringBean信息。
          }
      }
      ```
   
      ```java
       public abstract class AbstractConfig implements Serializable {
       /**
           * Add {@link AbstractConfig instance} into {@link ConfigManager}
           * <p>
           * Current method will invoked by Spring or Java EE container automatically, or should be triggered manually.
           *
           * @see ConfigManager#addConfig(AbstractConfig)
           * @since 2.7.5
           */
          @PostConstruct
          public void addIntoConfigManager() {
              ApplicationModel.getConfigManager().addConfig(this);
          }
           // ... ...
      }
      ```
   
      注：Dubbo的SPI扩展时，会结合Spring容器来进行ConfigBean(ApplicationConfig , ConsumerConfig , RegistryConfig , ServiceBean ...)的实例化工作。继承于`ExtensionFactory` `org.apache.dubbo.common.extension.factory.AdaptiveExtensionFactory`用于存放获取实例化对象的工厂。
   
      ```java
      /**
       * AdaptiveExtensionFactory
       */
      @Adaptive
      public class AdaptiveExtensionFactory implements ExtensionFactory {
      
          private final List<ExtensionFactory> factories;
      
          public AdaptiveExtensionFactory() {
              // 这里加载ExtensionFactory的实例 SpiExtensionFactory和SpringExtensionFactory
              ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
              List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
              for (String name : loader.getSupportedExtensions()) {
                  list.add(loader.getExtension(name));
              }
              factories = Collections.unmodifiableList(list);
          }
      
          @Override
          public <T> T getExtension(Class<T> type, String name) {
              for (ExtensionFactory factory : factories) {
                  T extension = factory.getExtension(type, name);
                  if (extension != null) {
                      return extension;
                  }
              }
              return null;
          }
      
      }
      ```
   
      
   
   ## Dubbo 启动过程
   
   1. 启动引导类 `org.apache.dubbo.config.bootstrap.DubboBootstrap`
   
      ```java
      public class DubboBootstrap extends GenericEventListener {
          
           /**
           * 初始化
           * Initialize
           */
          private void initialize() {
              if (!initialized.compareAndSet(false, true)) {
                  return;
              }
      		
              ApplicationModel.initFrameworkExts();
      
              startConfigCenter();
      
              useRegistryAsConfigCenterIfNecessary();
      
              loadRemoteConfigs();
      
              checkGlobalConfigs();
      
              initMetadataService();
      
              initEventListener();
      
              if (logger.isInfoEnabled()) {
                  logger.info(NAME + " has been initialized!");
              }
          }
      
      
      	// ... ...
      	/**
           * Start the bootstrap
           */
          public DubboBootstrap start() {
              if (started.compareAndSet(false, true)) {
                  ready.set(false);
                  initialize(); 
                  if (logger.isInfoEnabled()) {
                      logger.info(NAME + " is starting...");
                  }
                  // 1. export Dubbo Services
                  exportServices();
      
                  // Not only provider register
                  if (!isOnlyRegisterProvider() || hasExportedServices()) {
                      // 2. export MetadataService
                      exportMetadataService();
                      //3. Register the local ServiceInstance if required
                      registerServiceInstance();
                  }
      
                  referServices();
                  if (asyncExportingFutures.size() > 0) {
                      new Thread(() -> {
                          try {
                              this.awaitFinish();
                          } catch (Exception e) {
                              logger.warn(NAME + " exportAsync occurred an exception.");
                          }
                          ready.set(true);
                          if (logger.isInfoEnabled()) {
                              logger.info(NAME + " is ready.");
                          }
                      }).start();
                  } else {
                      ready.set(true);
                      if (logger.isInfoEnabled()) {
                          logger.info(NAME + " is ready.");
                      }
                  }
                  if (logger.isInfoEnabled()) {
                      logger.info(NAME + " has started.");
                  }
              }
              return this;
          }
          // ... ...
      }
      ```
   
      
   
   2. 服务发布，`DubboBootstrap#exportServices()` 暴露通过 Service
   
      ```java
      public class DubboBootstrap extends GenericEventListener {
      	// 暴露服务
      	private void exportServices() {
              // 对应配置文件中的<dubbo:service> 标签 Spring在初始化Bean的时候会添加到ConfigManager中
              configManager.getServices().forEach(sc -> {
                  // TODO, compatible with ServiceConfig.export()
                  ServiceConfig serviceConfig = (ServiceConfig) sc;
                  serviceConfig.setBootstrap(this);
      
                  if (exportAsync) {
                      ExecutorService executor = executorRepository.getServiceExporterExecutor();
                      Future<?> future = executor.submit(() -> {
                          sc.export();
                          exportedServices.add(sc);
                      });
                      asyncExportingFutures.add(future);
                  } else {
                      sc.export();
                      exportedServices.add(sc);
                  }
              });
          }
      }
      ```
   
      
   
      
   
   3. 服务注册
   
      
   
   

# 备注

可参考dubbo官方文档：http://dubbo.apache.org/zh-cn/docs/user/quick-start.html

