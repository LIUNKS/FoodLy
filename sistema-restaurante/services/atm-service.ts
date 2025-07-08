import { apiClient } from './apiClient';

// Interfaz que representa un ATM según la respuesta de la API
export interface ATMDTO {
  id_atm: string;
  name_atm: string;
  alias: string;
  email: string;
  phone: string;
  dni: string;
  is_active: boolean;
}

// Interfaz para la respuesta del servidor, según el formato en Swagger
export interface ATMResponseDTO {
  status: number;
  message: string;
  data: ATMDTO[];
}

// Interfaz auxiliar para manejar posibles variaciones en la respuesta
export interface ATMResponseWrapper {
  status?: number;
  message?: string;
  data?: ATMDTO[] | ATMResponseDTO;
}

export const atmService = {
  /**
   * Obtener lista de todos los ATMs
   * @returns Promise con la lista completa de ATMs
   */
  getATMList: async (): Promise<ATMResponseDTO> => {
    try {
      console.log("🏧 Obteniendo lista de ATMs...");
      console.log("🔍 URL de la petición:", 'atm/list');
      
      // Utilizando el endpoint exacto como se muestra en el swagger
      const response = await apiClient.get<ATMResponseDTO | ATMDTO[]>('atm/list');
      console.log("✅ Respuesta completa:", response);
      
      // Manejar diferentes formatos de respuesta
      if (Array.isArray(response)) {
        // La respuesta es directamente un array de ATMs
        console.log("📊 Formato de respuesta: Array directo de ATMs");
        return {
          status: 200,
          message: "ATMs obtenidos exitosamente",
          data: response
        };
      } else if (response && typeof response === 'object') {
        if ('data' in response && Array.isArray(response.data)) {
          // La respuesta tiene el formato esperado { status, message, data }
          console.log("📊 Formato de respuesta: { status, message, data }");
          return response as ATMResponseDTO;
        } else {
          // Otro formato de objeto
          console.warn("⚠️ Formato de respuesta inesperado:", response);
          return {
            status: 200,
            message: "Datos obtenidos en formato no estándar",
            data: []
          };
        }
      }
      
      console.warn("⚠️ Respuesta no reconocida:", response);
      return {
        status: 200,
        message: "Respuesta recibida pero en formato no reconocido",
        data: []
      };
    } catch (error) {
      console.error("❌ Error al obtener ATMs:", error);
      console.error("❌ Detalles del error:", error);
      
      // Crear respuesta de error simulada para evitar que falle la interfaz
      const errorResponse: ATMResponseDTO = {
        status: 0,
        message: "Error de conexión al servidor",
        data: []
      };
      
      // Lanzar un error más descriptivo
      const errorMsg = error instanceof Error ? error.message : "Error de conexión desconocido";
      console.error(`Error detallado: ${errorMsg}`);
      
      return errorResponse;
    }
  },

  /**
   * Obtener lista de ATMs disponibles (activos)
   * @returns Promise con la lista de ATMs activos
   */
  getActiveATMs: async (): Promise<ATMDTO[]> => {
    try {
      console.log("🏧 Obteniendo lista de ATMs activos...");
      const response = await atmService.getATMList();
      
      // Comprobar si la respuesta contiene datos
      if (!response.data || !Array.isArray(response.data)) {
        console.warn("⚠️ No se recibieron datos de ATMs o el formato es incorrecto");
        return [];
      }
      
      // Filtrar solo los ATMs activos
      const activeATMs = response.data.filter(atm => atm.is_active === true);
      
      console.log("✅ ATMs activos encontrados:", activeATMs.length);
      return activeATMs;
    } catch (error) {
      console.error("❌ Error al obtener ATMs activos:", error);
      console.error("Detalles del error:", error instanceof Error ? error.message : error);
      // Devolver un array vacío en lugar de propagar el error
      return [];
    }
  },

  /**
   * Obtener lista de ATMs inactivos (no disponibles)
   * @returns Promise con la lista de ATMs inactivos
   */
  getInactiveATMs: async (): Promise<ATMDTO[]> => {
    try {
      console.log("🏧 Obteniendo lista de ATMs inactivos...");
      const response = await atmService.getATMList();
      
      // Comprobar si la respuesta contiene datos
      if (!response.data || !Array.isArray(response.data)) {
        console.warn("⚠️ No se recibieron datos de ATMs o el formato es incorrecto");
        return [];
      }
      
      // Filtrar solo los ATMs inactivos
      const inactiveATMs = response.data.filter(atm => atm.is_active === false);
      
      console.log("✅ ATMs inactivos encontrados:", inactiveATMs.length);
      return inactiveATMs;
    } catch (error) {
      console.error("❌ Error al obtener ATMs inactivos:", error);
      return [];
    }
  },

  /**
   * Obtener lista completa de ATMs con información adicional de disponibilidad
   * @returns Promise con la lista completa de ATMs y su estado
   */
  getAllATMsWithStatus: async (): Promise<ATMDTO[]> => {
    try {
      console.log("🏧 Obteniendo lista completa de ATMs con estado...");
      console.log("🔍 Usando endpoint: 'atm/list'");
      
      // Llamada directa al API para evitar posibles errores en cascada
      const response = await apiClient.get<ATMResponseDTO>('atm/list');
      
      console.log("📄 Respuesta del servidor para ATMs:", response);
      
      // Comprobar si la respuesta contiene datos
      if (!response.data || !Array.isArray(response.data)) {
        // Verificar estructura anidada (si data está dentro de data)
        if (response && typeof response === 'object' && 'data' in response && Array.isArray((response as any).data)) {
          console.log("✅ ATMs encontrados en formato anidado:", (response as any).data.length);
          return (response as any).data;
        }
        
        console.warn("⚠️ No se recibieron datos de ATMs o el formato es incorrecto");
        console.warn("⚠️ Estructura de respuesta:", response);
        return [];
      }
      
      console.log("✅ Total de ATMs encontrados:", response.data.length);
      return response.data;
    } catch (error) {
      console.error("❌ Error al obtener todos los ATMs:", error);
      console.error("📌 Detalles del error:", error instanceof Error ? error.message : String(error));
      throw error; // Propagar el error para un mejor manejo
    }
  },

  /**
   * Asignar ATM a una caja
   * @param boxId ID de la caja
   * @param atmId ID del ATM (empleado)
   * @returns Promise con la respuesta del servidor
   */
  assignATMToBox: async (boxId: string, atmId: string): Promise<any> => {
    try {
      console.log("🔗 Asignando ATM a caja:", { boxId, atmId });
      const response = await apiClient.post(`/box/${boxId}/assign-atm/${atmId}`);
      console.log("✅ ATM asignado exitosamente:", response);
      return response;
    } catch (error) {
      console.error("❌ Error al asignar ATM:", error);
      throw error;
    }
  }
};
