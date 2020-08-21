INSERT INTO base_role (id, role_code, name, deleted) VALUES (1, 'admin', '管理员', 0);
INSERT INTO base_user (id,username,password,salt) VALUES(1,'administrator','56de734854c7e9bcb06f3feca836bd7cfee00758561a059d0ce1271984586776','48a7ebfb7af6f34f89bb58acd45962d6');
INSERT INTO base_user_role (user_id, role_id) VALUES (1, 1);