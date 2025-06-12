// services/apiClient.ts
import { API_BASE_URL } from './api';

export class ApiError extends Error {
  status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
    this.name = 'ApiError';
  }
}

class ApiClient {
  private baseURL: string;
  private onTokenExpired?: () => void;

  constructor(baseURL: string = API_BASE_URL) {
    this.baseURL = baseURL;
  }

  // Método para registrar callback cuando el token expire
  onTokenExpiredCallback(callback: () => void) {
    this.onTokenExpired = callback;
  }

  // Obtener token del localStorage
  private getToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem('authToken');
  }

  // Método para hacer requests con manejo automático de token expirado
  async request<T>(
    endpoint: string, 
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseURL}${endpoint}`;
    const token = this.getToken();

    console.log('🌐 ApiClient Request:', {
      url,
      method: options.method || 'GET',
      hasToken: !!token,
      tokenPreview: token ? `${token.substring(0, 20)}...` : 'No token'
    });

    // Configurar headers por defecto
    const defaultHeaders: HeadersInit = {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` }),
    };

    // Merge headers
    const headers = {
      ...defaultHeaders,
      ...options.headers,
    };

    try {
      console.log('📤 Sending request:', {
        url,
        method: options.method || 'GET',
        headers,
        body: options.body
      });
      
      const response = await fetch(url, {
        ...options,
        headers,
      });

      console.log('📥 Response received:', {
        url,
        status: response.status,
        statusText: response.statusText,
        ok: response.ok
      });      // Verificar si el token ha expirado
      if (response.status === 401 || response.status === 403) {
        console.warn('Token expirado o acceso no autorizado');
        
        // Disparar evento para componentes UI
        if (typeof window !== 'undefined') {
          window.dispatchEvent(new CustomEvent('tokenExpired'));
        }
        
        // Ejecutar callback de token expirado
        if (this.onTokenExpired) {
          this.onTokenExpired();
        }
        
        throw new ApiError(
          'Su sesión ha expirado. Por favor, inicie sesión nuevamente.',
          response.status
        );
      }

      // Verificar otros errores HTTP
      if (!response.ok) {
        let errorData;
        let errorMessage = `Error ${response.status}: ${response.statusText}`;
        
        try {
          const responseText = await response.text();
          console.error('🚫 Error Response Body:', responseText);
          
          if (responseText) {
            try {
              errorData = JSON.parse(responseText);
              errorMessage = errorData.message || errorData.error || errorMessage;
            } catch {
              // Si no es JSON, usar el texto tal como está
              errorMessage = responseText || errorMessage;
            }
          }
        } catch (readError) {
          console.error('Error reading response body:', readError);
        }

        console.error('🚫 Request failed:', {
          url,
          status: response.status,
          statusText: response.statusText,
          errorMessage,
          errorData
        });

        throw new ApiError(errorMessage, response.status);
      }

      // Parsear respuesta JSON
      const responseText = await response.text();
      return responseText ? JSON.parse(responseText) : ({} as T);

    } catch (error) {
      if (error instanceof ApiError) {
        throw error;
      }
      
      // Error de red u otro tipo de error
      console.error('Error en request API:', error);
      throw new ApiError(
        'Error de conexión. Verifique su conexión a internet.',
        0
      );
    }
  }

  // Métodos de conveniencia para diferentes tipos de requests
  async get<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    return this.request<T>(endpoint, { ...options, method: 'GET' });
  }

  async post<T>(endpoint: string, data?: any, options: RequestInit = {}): Promise<T> {
    return this.request<T>(endpoint, {
      ...options,
      method: 'POST',
      body: data ? JSON.stringify(data) : undefined,
    });
  }

  async put<T>(endpoint: string, data?: any, options: RequestInit = {}): Promise<T> {
    return this.request<T>(endpoint, {
      ...options,
      method: 'PUT',
      body: data ? JSON.stringify(data) : undefined,
    });
  }

  async patch<T>(endpoint: string, data?: any, options: RequestInit = {}): Promise<T> {
    return this.request<T>(endpoint, {
      ...options,
      method: 'PATCH',
      body: data ? JSON.stringify(data) : undefined,
    });
  }

  async delete<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    return this.request<T>(endpoint, { ...options, method: 'DELETE' });
  }
}

// Crear instancia singleton
export const apiClient = new ApiClient();