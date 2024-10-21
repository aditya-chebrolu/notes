# CSRF Attacks and Prevention

## What is a CSRF Attack?
- CSRF (Cross-Site Request Forgery) is a type of security vulnerability.
- It tricks the victim's browser into making unwanted requests to a website where the user is authenticated.
- The attack exploits the trust a website has in a user's browser.

## How CSRF Attacks Work
1. User authenticates with a legitimate website (e.g., bank.com).
2. User visits a malicious website (evil.com) while still authenticated to the legitimate site.
3. The malicious site tricks the browser into sending a request to the legitimate site.
4. The browser automatically includes authentication cookies with the request.
5. The legitimate site processes the request as it appears to come from the authenticated user.

## Key Points About CSRF
- Doesn't require stealing user credentials.
- Exploits how browsers handle cookies in cross-origin requests.
- Cookies are sent based on the request's destination, not its origin.

## CSRF Prevention Techniques
1. CSRF Tokens:
   - Server generates a unique token per session or request.
   - Token is included in forms or as a custom header.
   - Server validates the token for each state-changing request.

2. Implementation in Spring Boot:
   - Use `@EnableWebSecurity` and configure `CsrfTokenRepository`.
   - Include the token in forms (hidden field) or AJAX requests (custom header).
   - Spring Security automatically validates the token.

3. Cookie Settings:
   - Use SameSite cookie attribute to restrict cookie transmission.

4. Additional Measures:
   - Use POST for state-changing operations (instead of GET).
   - Implement proper session management.

## Example Spring Boot Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
```

## Best Practices
- Always use CSRF protection for authenticated sessions.
- Combine CSRF protection with other security measures (HTTPS, Content Security Policy).
- Regularly update and patch your frameworks and libraries.
- Educate developers about CSRF and secure coding practices.

