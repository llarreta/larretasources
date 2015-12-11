
insert into [SCHEMMA].security (id, loginPage, loginProcessingUrl, defaultSuccessUrl, failureUrl, usernameParameter, passwordParameter, logoutUrl, logoutSuccessUrl, deleteCookies) 
						values (1, '/app/login', '/app/j_spring_security_check', '/app/home', '/app/login', 'username', 'password', '/app/logout', '/app/login', 'JSESSIONID');

insert into [SCHEMMA].securityMatcher (id, matcherType, pattern, idSecurity) values (1, 'roles', '/app/home', 1);
insert into [SCHEMMA].securityMatcher (id, matcherType, pattern, idSecurity) values (2, 'permitAll', '/app/login', 1);
insert into [SCHEMMA].securityMatcher (id, matcherType, pattern, idSecurity) values (3, 'permitAll', '/app/confirm**', 1);
insert into [SCHEMMA].securityMatcher (id, matcherType, pattern, idSecurity) values (4, 'permitAll', '/app/javax.faces.resource/**', 1);
insert into [SCHEMMA].securityMatcher (id, matcherType, pattern, idSecurity) values (5, 'authenticated', '/**', 1);

insert into [SCHEMMA].securityMatcherRoles (idRole, idSecurityMatcher) values (2, 1);




