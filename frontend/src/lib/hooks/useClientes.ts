import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { clientesApi } from "@/lib/api/clientes-api";
import type { ClienteCreateDTO, ClienteUpdateDTO } from "@/types/cliente";
import { toast } from "@/lib/utils/toast";

/**
 * Hook para obtener todos los clientes
 */
export function useClientes(params?: {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: string;
}) {
  return useQuery({
    queryKey: ["clientes", params],
    queryFn: () => clientesApi.getAll(params),
  });
}

/**
 * Hook para buscar clientes
 */
export function useSearchClientes(query: string, params?: { page?: number; size?: number }) {
  return useQuery({
    queryKey: ["clientes", "search", query, params],
    queryFn: () => clientesApi.search(query, params),
    enabled: !!query,
  });
}

/**
 * Hook para obtener un cliente por ID
 */
export function useCliente(id: number) {
  return useQuery({
    queryKey: ["clientes", id],
    queryFn: () => clientesApi.getById(id),
    enabled: !!id,
  });
}

/**
 * Hook para crear un cliente
 */
export function useCreateCliente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: ClienteCreateDTO) => clientesApi.create(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["clientes"] });
      toast.success("Cliente creado exitosamente");
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || "Error al crear cliente");
    },
  });
}

/**
 * Hook para actualizar un cliente
 */
export function useUpdateCliente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: ClienteUpdateDTO }) =>
      clientesApi.update(id, data),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ["clientes"] });
      queryClient.invalidateQueries({ queryKey: ["clientes", variables.id] });
      toast.success("Cliente actualizado exitosamente");
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || "Error al actualizar cliente");
    },
  });
}

/**
 * Hook para eliminar un cliente
 */
export function useDeleteCliente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => clientesApi.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["clientes"] });
      toast.success("Cliente eliminado exitosamente");
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || "Error al eliminar cliente");
    },
  });
}
