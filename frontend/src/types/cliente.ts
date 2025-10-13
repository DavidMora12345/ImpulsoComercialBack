export interface Cliente {
  id: number;
  cedula: string;
  apellido: string;
  nombre: string;
  nombreCompleto: string;
  nombreComercial?: string;
  direccion?: string;
  telefono?: string;
  celular?: string;
  email: string;
  fechaNacimiento?: string;
  estadoCivil?: string;
  discapacitado: number;
  porcentajeDiscapacidad?: number;
  entidadReligiosaExonerada: number;
  porcentajeExoneracion?: number;
  idTipoPersoneriaJuridica: number;
  idNacionalidad?: number;
  idConyugue?: number;
  idRepresentanteLegal?: number;
  estado: number;
  terceraEdad: boolean;
  tieneDescuentoDiscapacidad: boolean;
}

export interface ClienteCreateDTO {
  cedula: string;
  apellido: string;
  nombre: string;
  nombreComercial?: string;
  direccion?: string;
  telefono?: string;
  celular?: string;
  email: string;
  fechaNacimiento?: string;
  estadoCivil?: string;
  discapacitado?: number;
  porcentajeDiscapacidad?: number;
  entidadReligiosaExonerada?: number;
  porcentajeExoneracion?: number;
  idTipoPersoneriaJuridica: number;
  idNacionalidad?: number;
  idConyugue?: number;
  idRepresentanteLegal?: number;
}

export interface ClienteUpdateDTO {
  apellido?: string;
  nombre?: string;
  nombreComercial?: string;
  direccion?: string;
  telefono?: string;
  celular?: string;
  email?: string;
  fechaNacimiento?: string;
  estadoCivil?: string;
  discapacitado?: number;
  porcentajeDiscapacidad?: number;
  entidadReligiosaExonerada?: number;
  porcentajeExoneracion?: number;
  idNacionalidad?: number;
  idConyugue?: number;
  idRepresentanteLegal?: number;
}

export interface PageResponse<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
}

export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
}
