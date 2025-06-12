"use client";

import { createContext, useState, useEffect, type ReactNode } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import { login as loginService } from "@/services/auth-service";
import { apiClient } from "@/services/apiClient";

interface User {
  id: string;
  name: string;
  email: string;
  dni: string;
  role: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  isLoading: boolean;
  error: string | null;
}

export const AuthContext = createContext<AuthContextType>({
  user: null,
  token: null,
  login: async () => {},
  logout: () => {},
  isLoading: false,
  error: null,
});

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);  const router = useRouter();

  const logout = () => {
    Cookies.remove("user");
    localStorage.removeItem("authToken");
    setUser(null);
    setToken(null);
    router.push("/");
  };

  useEffect(() => {
    const storedUser = Cookies.get("user");
    const storedToken = localStorage.getItem("authToken"); // <-- cargar token
    if (storedUser) {
      setUser(JSON.parse(storedUser));
      if (storedToken) setToken(storedToken);
    }

    // Configurar callback para el logout automático cuando el token expire
    apiClient.onTokenExpiredCallback(() => {
      console.warn('Token expirado, cerrando sesión automáticamente...');
      logout();
    });
  }, []);
  const login = async (username: string, password: string) => {
    setIsLoading(true);
    setError(null);
  
    try {
      const response = await loginService({ username, password });

      const userData: User = {
        id: response.data.id_admin,
        name: response.data.name_admin,
        email: response.data.email_admin,
        dni: response.data.dni_admin,
        role: response.role,
      };

      Cookies.set("user", JSON.stringify(userData), { expires: 7 });
      localStorage.setItem("authToken", response.token);
      setToken(response.token);  // <-- guarda el token en el estado también
      setUser(userData);

      // Redirigir según el rol
      if (userData.role === "cocina") {
        router.push("/cocina");
      } else {
        router.push("/apertura-cierre");
      }    } catch (err) {
      // Registrar el error para fines de desarrollo pero sin mostrarlo en producción
      if (process.env.NODE_ENV !== 'production') {
        console.error("Error en proceso de login:", err);
      }
      
      // Manejar y establecer el mensaje de error de forma más amigable
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError("Error desconocido durante la autenticación. Por favor, intente nuevamente.");
      }
      
      // Re-lanzar el error para que los componentes también puedan manejarlo si es necesario
      throw err;
    } finally {      setIsLoading(false);
    }
  };

  return (
    <AuthContext.Provider value={{ user,token, login, logout, isLoading, error }}>
      {children}
    </AuthContext.Provider>
  );
};

// "use client"

// import { createContext, useState, useEffect, type ReactNode } from "react"
// import { useRouter } from "next/navigation"
// import Cookies from "js-cookie"

// interface User {
//   id: number
//   username: string
//   role: string
// }

// interface AuthContextType {
//   user: User | null
//   login: (username: string, password: string) => Promise<void>
//   logout: () => void
//   isLoading: boolean
//   error: string | null
// }

// export const AuthContext = createContext<AuthContextType>({
//   user: null,
//   login: async () => {},
//   logout: () => {},
//   isLoading: false,
//   error: null,
// })

// interface AuthProviderProps {
//   children: ReactNode
// }

// export const AuthProvider = ({ children }: AuthProviderProps) => {
//   const [user, setUser] = useState<User | null>(null)
//   const [isLoading, setIsLoading] = useState(false)
//   const [error, setError] = useState<string | null>(null)
//   const router = useRouter()

//   // Verificar si hay un usuario en cookies al cargar
//   useEffect(() => {
//     const storedUser = Cookies.get("user")
//     if (storedUser) {
//       setUser(JSON.parse(storedUser))
//     }
//   }, [])

//   const login = async (username: string, password: string) => {
//     setIsLoading(true)
//     setError(null)

//     try {
//       // Simulación de llamada a API
//       // En producción, esto sería una llamada real a tu backend de Spring Boot
//       await new Promise((resolve) => setTimeout(resolve, 1000))

//       // Simulación de respuesta
//       let userData: User
//       let redirectUrl = "/apertura-cierre" // URL predeterminada para redirección

//       // Asignar rol según el nombre de usuario (solo para demostración)
//       if (username === "admin") {
//         userData = { id: 1, username: "admin", role: "admin" }
//       } else if (username === "cajero") {
//         userData = { id: 2, username: "cajero", role: "cajero" }
//       } else if (username === "cocina") {
//         userData = { id: 3, username: "cocina", role: "cocina" }
//         redirectUrl = "/cocina" // Redirigir a cocina si el rol es cocina
//       } else {
//         throw new Error("Usuario no encontrado")
//       }

//       // Guardar usuario en cookies para que el middleware pueda acceder a él
//       Cookies.set("user", JSON.stringify(userData), { expires: 7 }) // Expira en 7 días
//       setUser(userData)
      
//       // Redirigir al usuario después de iniciar sesión
//       console.log("Redirigiendo a:", redirectUrl)
//       router.push(redirectUrl)
//     } catch (err) {
//       setError(err instanceof Error ? err.message : "Error de autenticación")
//     } finally {
//       setIsLoading(false)
//     }
//   }

//   const logout = () => {
//     Cookies.remove("user")
//     setUser(null)
//     router.push("/")
//   }

//   return <AuthContext.Provider value={{ user, login, logout, isLoading, error }}>{children}</AuthContext.Provider>
// }
