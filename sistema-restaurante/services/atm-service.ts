import { apiClient } from './apiClient';

export interface ATMDTO {
  id_atm: string;
  name?: string;
  location?: string;
  is_active?: boolean;
  created_at?: string;
}

export interface ATMListResponse {
  data: ATMDTO[];
  message?: string;
}

export const atmService = {
  // Obtener lista de ATMs disponibles
  getATMList: async (): Promise<ATMListResponse> => {
    try {
      console.log("🏧 Obteniendo lista de ATMs...");
      const response = await apiClient.get<ATMDTO[]>('/atm/list');
      console.log("✅ ATMs obtenidos:", response);
      return {
        data: response,
        message: "ATMs obtenidos exitosamente"
      };
    } catch (error) {
      console.error("❌ Error al obtener ATMs:", error);
      throw error;
    }
  },

  // Asignar ATM a una caja
  assignATMToBox: async (boxId: string, atmId: string): Promise<any> => {
    try {
      console.log("🔗 Asignando ATM a caja:", { boxId, atmId });
      const response = await apiClient.put(`/box/${boxId}/assign-atm/${atmId}`);
      console.log("✅ ATM asignado exitosamente:", response);
      return response;
    } catch (error) {
      console.error("❌ Error al asignar ATM:", error);
      throw error;
    }
  }
};
