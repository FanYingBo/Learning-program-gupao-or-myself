drop table if exists tb_normal_custom;
/*==============================================================*/
/* table: tb_normal_custom                                      */
/*==============================================================*/
create table tb_normal_custom
(
   name                 varchar(16) not null,
   age                  int,
   sex                  int,
   is_vip               int,
   birth                date,
   createdate           timestamp,
   job                  varchar(32),
   id_number            varchar(20),
   addr                 varchar(64),
   primary key (name)
);
drop table if exists tb_vip_custom;

/*==============================================================*/
/* table: tb_vip_custom                                         */
/*==============================================================*/
create table tb_vip_custom
(
   name                 varchar(16) not null,
   member_type          int,
   pay_date             datetime,
   expire_date          datetime,
   primary key (name)
);

alter table tb_vip_custom add constraint fk_reference_1 foreign key (name)
      references tb_normal_custom (name) on delete restrict on update restrict;

drop table if exists tb_product_info;
/*==============================================================*/
/* table: tb_product_info                                       */
/*==============================================================*/
create table tb_product_info
(
   product_code         varchar(32) not null,
   description          varchar(64),
   sale_type            int,
   supply_id            int,
   primary key (product_code)
);


drop table if exists tb_custom_order;
/*==============================================================*/
/* table: tb_custom_order                                       */
/*==============================================================*/
create table tb_custom_order
(
   order_code            varchar(32) not null,
   product_code         varchar(32),
   name                 varchar(16),
   create_date          datetime,
   is_receive           int,
   primary key (order_code)
);

alter table tb_custom_order add constraint fk_reference_2 foreign key (product_code)
      references tb_product_info (product_code) on delete restrict on update restrict;

alter table tb_custom_order add constraint fk_reference_3 foreign key (name)
      references tb_normal_custom (name) on delete restrict on update restrict;

drop table if exists tb_custom_look_record;

/*==============================================================*/
/* table: tb_custom_look_record                                 */
/*==============================================================*/
create table tb_custom_look_record
(
   product_code         varchar(32),
   look_date            timestamp,
   name                 varchar(16)
);

alter table tb_custom_look_record add constraint fk_reference_4 foreign key (product_code)
references tb_product_info (product_code) on delete restrict on update restrict;

alter table tb_custom_look_record add constraint fk_reference_5 foreign key (name)
references tb_normal_custom (name) on delete restrict on update restrict;
