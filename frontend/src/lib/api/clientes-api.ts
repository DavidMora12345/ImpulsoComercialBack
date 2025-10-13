import api from "./axios-instance";
import type {
  Cliente,
  ClienteCreateDTO,
  ClienteUpdateDTO,
  PageResponse,
  ApiResponse,
} from "@/types/cliente";

export const clientesApi = {
  /**
   * Obtiene todos los clientes (paginado)
   */
  getAll: async (params?: {
    page?: number;
    size?: number;
    sortBy?: string;
    sortDir?: string;
  }): Promise<PageResponse<Cliente>> => {
    const response = await api.get<PageResponse<Cliente>>("/api/clientes", {
      params: {
        page: params?.page || 0,
        size: params?.size || 10,
        sortBy: params?.sortBy || "id",
        sortDir: params?.sortDir || "ASC",
      },
    });
    return response.data;
  },

  /**
   * Busca clientes por query
   */
  search: async (
    query: string,
    params?: { page?: number; size?: number }
  ): Promise<PageResponse<Cliente>> => {
    const response = await api.get<PageResponse<Cliente>>(
      "/api/clientes/search",
      {
        params: {
          query,
          page: params?.page || 0,
          size: params?.size || 10,
        },
      }
    );
    return response.data;
  },

  /**
   * Obtiene un cliente por ID
   */
  getById: async (id: number): Promise<Cliente> => {
    const response = await api.get<ApiResponse<Cliente>>(
      `/api/clientes/${id}`
    );
    return response.data.data;
  },

  /**
   * Obtiene un cliente por cédula
   */
  getByCedula: async (cedula: string): Promise<Cliente> => {
    const response = await api.get<ApiResponse<Cliente>>(
      `/api/clientes/cedula/${cedula}`
    );
    return response.data.data;
  },

  /**
   * Crea un nuevo cliente
   */
  create: async (data: ClienteCreateDTO): Promise<Cliente> => {
    const response = await api.post<ApiResponse<Cliente>>(
      "/api/clientes",
      data
    );
    return response.data.data;
  },

  /**
   * Actualiza un cliente
   */
  update: async (id: number, data: ClienteUpdateDTO): Promise<Cliente> => {
    const response = await api.put<ApiResponse<Cliente>>(
      `/api/clientes/${id}`,
      data
    );
    return response.data.data;
  },

  /**
   * Elimina un cliente
   */
  delete: async (id: number): Promise<void> => {
    await api.delete(`/api/clientes/${id}`);
  },

  /**
   * Obtiene clientes con tercera edad
   */
  getTerceraEdad: async (params?: {
    page?: number;
    size?: number;
  }): Promise<PageResponse<Cliente>> => {
    const response = await api.get<PageResponse<Cliente>>(
      "/api/clientes/tercera-edad",
      {
        params: {
          page: params?.page || 0,
          size: params?.size || 10,
        },
      }
    );
    return response.data;
  },

  /**
   * Obtiene clientes con discapacidad
   */
  getDiscapacidad: async (params?: {
    page?: number;
    size?: number;
  }): Promise<PageResponse<Cliente>> => {
    const response = await api.get<PageResponse<Cliente>>(
      "/api/clientes/discapacidad",
      {
        params: {
          page: params?.page || 0,
          size: params?.size || 10,
        },
      }
    );
    return response.data;
  },
};
