INSERT INTO role (id,name,description,category,hidden) VALUES (1,'ROLE_ADMIN','مدير النظام',NULL,b'1');
INSERT INTO role (id,name,description,category,hidden) VALUES (2,'ROLE_EMPLOYEE','موظف',NULL,b'1');
INSERT INTO role (id,name,description,category,hidden) VALUES (3,'ROLE_DELEGATE','مندوب',NULL,b'1');
INSERT INTO role (id,name,description,category,hidden) VALUES (4,'ROLE_BENEFACTOR','متبرع',NULL,b'1');
INSERT INTO role (id,name,description,category) VALUES (5,'ROLE_TRACK_DELEGATES','المتابعة اللحظية للمناديب','EMPLOYEE');
INSERT INTO role (id,name,description,category) VALUES (6,'ROLE_MANAGE_DELEGATES','إدارة المناديب','ADMIN');
INSERT INTO role (id,name,description,category) VALUES (7,'ROLE_MANAGE_EMPLOYEES','إدارة الموظفين','ADMIN');
INSERT INTO role (id,name,description,category) VALUES (8,'ROLE_COLLECT_MONEY','استلام المبالغ','EMPLOYEE');
INSERT INTO role (id,name,description,category) VALUES (9,'ROLE_DEPOSIT_FINANCE','تسليم المبالغ للحسابات','EMPLOYEE');
INSERT INTO role (id,name,description,category) VALUES (10,'ROLE_CREATE_MANUAL_RECEIPT','انشاء سند يدوي','EMPLOYEE');
INSERT INTO role (id,name,description,category) VALUES (11,'ROLE_MANAGE_COUPONS','إدارة الكوبونات','ADMIN');
INSERT INTO role (id,name,description,category) VALUES (12,'ROLE_REPORT_COUPONS_INCOME','إيرادات الكوبونات','REPORT');
INSERT INTO role (id,name,description,category) VALUES (13,'ROLE_REPORT_DELEGATES_INCOME','إيرادات المناديب','REPORT');
INSERT INTO role (id,name,description,category) VALUES (14,'ROLE_REPORT_LOCATIONS_INCOME','إيرادات المواقع','REPORT');
INSERT INTO role (id,name,description,category) VALUES (15,'ROLE_REPORT_COLLECTION_DELEGATES','المبالغ المستلمة من المناديب','REPORT');
INSERT INTO role (id,name,description,category) VALUES (16,'ROLE_REPORT_FINANCE_DEPOSIT','المبالغ المسلمة للحسابات','REPORT');
INSERT INTO role (id,name,description,category) VALUES (17,'ROLE_MANAGE_SETTINGS','إدارة اعدادات النظام','ADMIN');
INSERT INTO role (id,name,description,category) VALUES (18,'ROLE_REPORT_BENEFACTORS','بيانات المحسنين','REPORT');


INSERT INTO country (active, has_emarat, name_arabic, name_english,creator_id,last_modifier_id,date_created,date_last_modified) VALUES (b'1', b'1', 'الإمارات', 'UAE','1','1','2019-05-22 23:16:20','2019-05-22 23:16:20');


INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'أبو ظبي', 'Abu Dhabi', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'الشارقة', 'Sharjah', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'الفجيرة', 'Fujairah', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'أم القيوين', 'Umm Al Quwain', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'دبي', 'Dubai', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'رأس الخيمة', 'Ras Al Khaimah', '1');
INSERT INTO emarah (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `country_id`) VALUES (b'1', '2019-05-22 23:17:22', '2019-05-22 23:17:23', 'عجمان', 'Ajman', '1');

INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'أبو ظبي', 'Abu Dhabi', '1');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'الشارقة', 'Sharjah', '2');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'الفجيرة', 'Fujairah', '3');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'أم القيوين', 'Umm Al Quwain', '4');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'دبي', 'Dubai', '5');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'رأس الخيمة', 'Ras Al Khaimah', '6');
INSERT INTO city (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`, `emarah_id`) VALUES (b'1', '2019-05-22 23:40:06', '2019-05-22 23:40:07', 'عجمان', 'Ajman', '7');

INSERT INTO branch (`active`, `date_created`, `date_last_modified`, `name_arabic`, `name_english`,city_id) VALUES (b'1', '2019-05-23 01:11:26', '2019-05-23 01:11:29', 'جميع الفروع', 'All Branches','1');

INSERT INTO `scef`.`application_config` (`sms_message`, `smsurl`, `sms_user_name`, `sms_user_password`) VALUES ('شكرا لتبرعكم لجمعية سعود بن صقر تم استلام مبلغ {amountParam} رقم الإيصال {receiptNumber}', 'http://smsWebSite.com/sendMessage?user={userNameParam}&pass={passwordParam}&ProviderID=1019&text={messageParam}&msisdn={mobileNumberParam}&encoding=2', 'testUser', '12345');

INSERT INTO `scef`.`user` (`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `display_name`, `enabled`, `password`, `type`, `user_name`,attribute1,branch_id,contract_type,city_id,country_id) VALUES (b'1', b'1', b'1', 'مدير النظام', b'1', '$2a$10$.D0bhzx38KqXvf0coGi7deQqAx1M/ijDDjGnk0U3a4b7fI7ZoeSyu', 'ADMIN', 'admin','Jue0vN2USgM+XWpfBMRzTg==','1','PERMANENT','3','1');
INSERT INTO `scef`.`user_details` (account_number,user_id) VALUES ('1','1');
update `scef`.`user` set user_details_id = '1' where id = '1';
INSERT INTO user_role (user_id, role_id,branch_id) VALUES ('1', '1','1');