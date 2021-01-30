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

INSERT INTO application_config (`sms_message`, `smsurl`, `sms_user_name`, `sms_user_password`) VALUES ('شكرا لتبرعكم لجمعية سعود بن صقر تم استلام مبلغ {amountParam} رقم الإيصال {receiptNumber}', 'http://smsWebSite.com/sendMessage?user={userNameParam}&pass={passwordParam}&ProviderID=1019&text={messageParam}&msisdn={mobileNumberParam}&encoding=2', 'testUser', '12345');

INSERT INTO `user` (`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `display_name`, `enabled`, `password`, `type`, `user_name`,attribute1,branch_id,contract_type,city_id,country_id) VALUES (b'1', b'1', b'1', 'مدير النظام', b'1', '$2a$10$.D0bhzx38KqXvf0coGi7deQqAx1M/ijDDjGnk0U3a4b7fI7ZoeSyu', 'ADMIN', 'admin','Jue0vN2USgM+XWpfBMRzTg==','1','PERMANENT','3','1');
INSERT INTO user_details (account_number,user_id) VALUES ('1','1');
update `user` set user_details_id = '1' where id = '1';
INSERT INTO user_role (user_id, role_id,branch_id) VALUES ('1', '1','1');



Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (0,1,'SUCCESS_CODE','تمت العملية نجاح','Success');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (-1,1,'SYSTEM_ERROR_CODE','خطأ غير متوقع','System error');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (1,1,'WRONG_CREDENTIALS','خطأ فى اسم المستخدم او كلمة المرور','Wrong username or password');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (2,1,'USER_DISABLED','تم ايقاف المستخدم','User is disabled');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (3,1,'USER_EXPIRED','انتهت صلاحية المستخدم','User is expired');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (4,1,'INVALID_PAYMENT_TYPE','طريقة الدفع المختارة غير موجودة','Selected payment type doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (5,1,'DELEGATE_NOT_EXIST','المفوض غير موجود','Delegate doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (6,1,'RECEIPT_DETAILS_REQUIRED','يجب اختيار كوبون او مشروع او كفالة','You must select a coupon or project or sponsorship');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (7,1,'COUPON_TYPE_NOT_EXIST','الكوبون غير موجود','Coupon doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (8,1,'COUPON_AMOUNT_LESS_MINIMUM','مبلغ التبرع اقل من المبلغ المسموح به للكوبون','Amount is less than coupon minimum amount');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (9,1,'DONATOR_NAME_REQUIRED','يجب ادخال اسم المتبرع','Please enter donator name');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (10,1,'COUPON_AMOUNT_REQUIRED','يجب ادخال مبلغ التبرع','Please enter amount');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (11,1,'CREDIT_CARD_TRANSACTION_NUMBER_REQUIRED','يجب ادخال رقم عملية الشراء الخاصة بالكريدت كارد','Please enter credit card transaction number');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (12,1,'ALL_CHEQUE_DETAILS_REQUIRED','يجب ادخال كود البنك ورقم الشيك وتاريخ الشيك','Please enter the bank code,cheque number and cheque date');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (13,1,'MISSING_NEW_PROJECT_INFO','من فضلك قم بإدخال جميع بيانات المشروع','Please enter all project information');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (14,1,'COUNTRY_NOT_EXIST','الدولة غير موجودة','Country doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (15,1,'PROJECT_TYPE_NOT_EXIST','نوع المشروع غير موجود','Project type doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (16,1,'PROJECT_STUDY_NOT_EXIST','دراسة المشروع غير موجودة','Project study doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (17,1,'DONATOR_NOT_EXIST','المتبرع غير موجود','Donator doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (18,1,'NEW_DONATOR_DETAILS_REQUIRED','من فضلك قم بإدخال بيانات المتبرع الجديد','Please enter new donator details');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (19,1,'MISSING_DONATE_CURRENT_PROJECT_INFO','من فضلك قم باختيار المشروع والمتبرع وادخال مبلغ التبرع','Please select project and donator and enter amount');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (20,1,'OLD_PROJECT_NOT_EXIST','المشروع القديم غير موجود','Old project doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (21,1,'MISSING_NEW_SPONSORSHIP_INFO','من فضلك قم باختيار االأيتام','Please select orphans');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (22,1,'GIFT_TYPE_NOT_EXIST','نوع الهدية غير موجود','Gift type doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (23,1,'SPONSOR_NOT_EXIST','الكافل غير موجود','Sponsor doesn''t exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (24,1,'DELEGATE_ID_REQUIRED','من فضلك قم بإدخال رقم المفوض','Please enter delegate id');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (25,1,'COUPON_TYPE_ID_REQUIRED','من فضلك قم بإدخال رقم الكوبون','Please enter coupon id');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (26,1,'GIFT_AMOUNT_REQUIRED','من فضلك قم بإدخال مبلغ الهدية','Please enter gift amount');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (27,1,'GIFT_TYPE_ID_REQUIRED','من فضلك قم بإدخال رقم الهدية','Please enter gift id');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (28,1,'ORPHAN_AMOUNT_REQUIRED','من فضلك قم بإدخال مبلغ التبرع لليتيم','Please enter sponsorship amount for orphan');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (29,1,'ORPHAN_ID_REQUIRED','من فضلك قم بإدخال رقم اليتيم','Please enter orphan id');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (30,1,'BANK_CODE_AND_ACCOUNT_NUMBER_REQUIRED','من فضلك قم بإدخال كود البنك ورقم الحساب','Please enter bank code and account number');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (31,1,'MISSING_OLD_SPONSORSHIP_INFO','من فضلك قم باختيار الكافل والأيتام المراد التبرع لهم','Please select sponsor and orphans');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (32,1,'FIRST_TITLE_NOT_EXIST','اللقب غير موجود','Title does not exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (33,1,'CHARITY_BOX_IN_USE','الحصالة قيد الاستخدام','Charity box is in use');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (34,1,'CHARITY_BOX_SAME_SOURCE_EXISTS','يوجد حصالة من نفس المصدر','There is s already a charity box from the same source');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (35,1,'BARCODE_NOT_EXIST','الباركود غير موجود','Barcode does not exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (37,1,'SUBLOCATION_ALREADY_HAS_CHARITYBOX','المكان يحتوى على حصالة','Sublocation already has a charitybox');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (38,1,'NEW_CHARITYBOX_NOT_EXIST_IN_DB','الحصالة الجديدة غير موجودة فى قاعدة البيانات','New charity box does not exist in database');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (39,1,'NEW_CHARITYBOX_MUST_BE_INACTIVE','الحصالة الجديدة يجب أن تكون غير فعالة','New charity box must be inactive');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (40,1,'ONLY_ACTIVE_CHARITYBOX_CAN_BE_COLLECTED','لا يمكن جمع الحصالة لأنها ليست فعالة','Cannot collect charity box because it is not active');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (41,1,'CANNOT_REPLACE_CHARITYBOX_WITH_ITSELF','لا يمكن استبدال الحصالة بنفسها !','Cannot replace charity box with itself');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (42,1,'CANNOT_CHECK_CHARITY_BOX','لا يمكن فحص الحصالة','Cannot check charity box');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (43,1,'CHARITYBOX_NOT_EXIST_IN_DB','الحصالة غير موجودة فى قاعدة البيانات','Charity box does not exist in database');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (44,1,'ONLY_ACTIVE_CHARITYBOX_CAN_BE_REMOVED','لا يمكن سحب الحصالة لأنها ليست فعالة','Cannot remove charity box because it is not active');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (45,1,'REGION_ALREADY_EXIST','المنطقة مضافة مسبقا','Region already exists');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (46,1,'LOCATION_ALREADY_EXIST','الحى مضاف مسبقا','Location already exists');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (47,1,'SUBLOCATION_ALREADY_EXIST','المكان مضاف مسبقا','Sub Location already exists');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (48,1,'CHARITYBOX_NUMBER_NOT_EXIST','رقم الحصالة غير موجود','Charity box number does not exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (49,1,'ORPHAN_AMOUNT_OR_GIFT_REQUIRED','يرجى ادخال مبلغ الكفالة او الهدية','Please enter sponsorship amount or gift amount');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (50,1,'COUPON_QRCODE_NOT_EXIST','كود الكوبون غير موجود','Coupon QR code does not exist');
Insert into ERROR_CODE (ID,ACTIVE,ERROR_CODE,ERROR_NAME_ARABIC,ERROR_NAME_ENGLISH) values (51,1,'NO_PERMISSION_TO_USE_MOBILE','ليس لديك صلاحية لاستخدام التطبيق','User does not have permission to use mobile');