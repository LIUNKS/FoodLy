// services/box-service.ts
import { apiClient } from './apiClient';

// Tipos basados en los DTOs del backend
export interface BoxNameDTO {
  name_box: string;
}

export interface BoxDTO {
  id_box: string;
  name_box: string;
  date: string;
  is_open: boolean;
  atm?: {
    id_atm: string;
    name_atm?: string;
    alias?: string;
    email?: string;
    phone?: string;
    dni?: string;
  } | null; // ATM puede ser null si no está asignado
}

export interface BoxResponseDTO {
  id_box: string;
  name_box: string;
  date: string;
  is_open: boolean;
}

export interface SuccessMessage<T> {
  status: number;
  message: string;
  data: T;
}

/**
 * Servicio para gestionar las operaciones relacionadas con cajas
 */
export class BoxService {
  
  /**
   * Crear una nueva caja
   * @param adminId ID del administrador
   * @param boxData Datos de la caja (nombre)
   * @returns Promise con la respuesta del servidor
   */
  async createBox(adminId: string, boxData: BoxNameDTO): Promise<SuccessMessage<BoxResponseDTO>> {
    return await apiClient.post<SuccessMessage<BoxResponseDTO>>(`box/${adminId}`, boxData);
  }

  /**
   * Obtener la lista de todas las cajas
   * @returns Promise con la lista de cajas
   */
  async getBoxList(): Promise<SuccessMessage<BoxDTO[]>> {
    return await apiClient.get<SuccessMessage<BoxDTO[]>>('box/list');
  }

  /**
   * Obtener información de una caja específica
   * @param boxId ID de la caja
   * @returns Promise con los datos de la caja
   */
  async getBoxInfo(boxId: string): Promise<SuccessMessage<BoxDTO>> {
    return await apiClient.get<SuccessMessage<BoxDTO>>(`box/${boxId}`);
  }

  /**
   * Obtener cajas asignadas a un cajero específico
   * @param atmId ID del cajero automático
   * @returns Promise con la lista de cajas del cajero
   */
  async getBoxesByAtm(atmId: string): Promise<SuccessMessage<BoxDTO[]>> {
    return await apiClient.get<SuccessMessage<BoxDTO[]>>(`box/by-atm/${atmId}`);
  }
  /**
   * Asignar un empleado (ATM) a una caja
   * @param boxId ID de la caja
   * @param atmId ID del empleado (ATM)
   * @returns Promise con la respuesta del servidor
   */
  async assignATMToBox(boxId: string, atmId: string): Promise<any> {
    console.log("🔗 Enviando solicitud para asignar empleado a caja:", { boxId, atmId });
    // Intentar con POST en lugar de PUT
    return await apiClient.post(`box/${boxId}/assign-atm/${atmId}`);
  }
}

// Exportar instancia singleton
export const boxService = new BoxService();
