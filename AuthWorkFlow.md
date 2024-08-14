# 🚀 Authentication Workflow in Spring Security

## 🔑 Key Concept

When working with Spring Security, always keep the following workflow in mind. It will help you understand the **core concepts** and how authentication is processed step by step.

---

## 📥 Request
- A client sends an **HTTP request** containing authentication credentials (username and password) to the server.

## 🔗 Filter Chain
- The request passes through a series of security filters configured in the **Spring Security Filter Chain**.

## 🛡️ UsernamePasswordAuthenticationFilter
- The `UsernamePasswordAuthenticationFilter` intercepts the request and extracts the username and password from it.
- It then creates a `UsernamePasswordAuthenticationToken` object containing these credentials but without authentication status.

## 🧩 AuthenticationManager
- The `AuthenticationManager` is responsible for handling the authentication process.
- It invokes the `authenticate` method, passing the `UsernamePasswordAuthenticationToken` for further processing.

## 🛠️ ProviderManager (AuthenticationManager Implementation)
- The `ProviderManager` is the default implementation of `AuthenticationManager`.
- It iterates through a list of configured `AuthenticationProvider` instances.

### 🔍 AuthenticationProvider 0
- The `ProviderManager` calls the `authenticate` method on the first `AuthenticationProvider`.
- If this provider supports the authentication token type, it processes the authentication.

### 🔄 AuthenticationProvider 1
- If the first `AuthenticationProvider` does not handle or fails to authenticate, the `ProviderManager` moves to the next `AuthenticationProvider` in the list.
- The process is repeated.

### 🧩 AuthenticationProvider n
- This continues until one of the `AuthenticationProvider` instances successfully authenticates the token or the list is exhausted.

## 🔐 DaoAuthenticationProvider
- `DaoAuthenticationProvider` is a common `AuthenticationProvider` used to authenticate using a username and password.
- It uses a `UserDetailsService` to look up the user by the provided username.
- The `loadUserByUsername` method of `UserDetailsService` returns a `UserDetails` object containing the user’s credentials and authorities.

## 🔒 Password Validation
- The `DaoAuthenticationProvider` compares the credentials from the `UserDetails` object with the credentials provided in the request.
- The password is typically hashed and compared using a `PasswordEncoder`.

## ✅ Successful Authentication
- If the credentials match, the `DaoAuthenticationProvider` creates a fully authenticated `Authentication` object (an updated `UsernamePasswordAuthenticationToken`).
- This object is then returned to the `ProviderManager`.

## 🗄️ SecurityContextHolder
- The `Authentication` object is stored in the `SecurityContextHolder`.
- This allows the authenticated user’s details to be accessed throughout the application during the request lifecycle.

## ❌ Failed Authentication
- If none of the `AuthenticationProvider` instances can authenticate the request, or if the credentials are invalid, an `AuthenticationException` is thrown.
- This exception is typically caught by the `ExceptionTranslationFilter`, which handles the response (e.g., redirecting to a login page or returning an error status).

---

By following this workflow, you can understand how Spring Security processes authentication requests, ensuring that the correct user credentials are validated and securely managed throughout the application.
