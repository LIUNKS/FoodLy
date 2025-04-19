// Este archivo servirá como interfaz entre tu frontend y el backend de Spring Boot

interface LoginResponse {
  token: string;
  user: {
    id: number;
    username: string;
    role: string;
  };
  redirectUrl: string;
}

interface LoginCredentials {
  username: string;
  password: string;
}

export async function login(
  credentials: LoginCredentials
): Promise<LoginResponse> {
  try {
    // Reemplaza esta URL con la URL real de tu API de Spring Boot
    const response = await fetch("http://tu-api-spring-boot/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(credentials),
    });

    if (!response.ok) {
      // Si el servidor responde con un error, lo manejamos
      const errorData = await response.json();
      throw new Error(errorData.message || "Error de autenticación");
    }

    // Parseamos la respuesta exitosa
    const data: LoginResponse = await response.json();
    return data;
  } catch (error) {
    console.error("Error en el servicio de autenticación:", error);
    throw error;
  }
}

export function saveAuthToken(token: string): void {
  localStorage.setItem("authToken", token);
}

export function getAuthToken(): string | null {
  return localStorage.getItem("authToken");
}

export function removeAuthToken(): void {
  localStorage.removeItem("authToken");
}

export function isAuthenticated(): boolean {
  return !!getAuthToken();
}
