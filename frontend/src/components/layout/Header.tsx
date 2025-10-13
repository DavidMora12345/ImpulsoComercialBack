"use client";

import { Search, Grid3x3, Bell, ChevronDown, ExternalLink, FileText, Briefcase, BarChart3 } from "lucide-react";
import { useState } from "react";

interface HeaderProps {
  onMenuClick: () => void;
  sidebarOpen: boolean;
}

export function Header({ onMenuClick, sidebarOpen }: HeaderProps) {
  const [sistemasOpen, setSistemasOpen] = useState(false);
  const [userMenuOpen, setUserMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-white">
      <div className="flex h-16 items-center px-6">
        
        {/* Logo y Título */}
        <div className="flex items-center gap-3">
          <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-blue-600">
            <span className="text-xl font-bold text-white">SC</span>
          </div>
          <div className="hidden md:block">
            <h1 className="text-lg font-semibold text-gray-900">
              Sistema Comercial
            </h1>
            <p className="text-xs text-gray-500">Municipio de Cuenca</p>
          </div>
        </div>

        {/* Búsqueda Global */}
        <div className="mx-6 flex-1 max-w-xl">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-gray-400" />
            <input
              type="text"
              placeholder="Buscar cliente, factura, predio..."
              className="w-full rounded-lg border border-gray-300 bg-gray-50 py-2 pl-10 pr-4 text-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
            />
          </div>
        </div>

        {/* Acciones Derecha */}
        <div className="flex items-center gap-2">
          
          {/* Dropdown Sistemas Integrados */}
          <div className="relative">
            <button
              onClick={() => setSistemasOpen(!sistemasOpen)}
              className="flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 transition-colors"
            >
              <Grid3x3 className="h-4 w-4" />
              <span className="hidden md:inline">Sistemas</span>
              <ChevronDown className="h-4 w-4" />
            </button>

            {/* Dropdown Menu */}
            {sistemasOpen && (
              <div className="absolute right-0 mt-2 w-64 rounded-lg border border-gray-200 bg-white shadow-lg">
                <div className="p-2">
                  <div className="px-3 py-2">
                    <p className="text-xs font-semibold text-gray-500">SISTEMAS INTEGRADOS</p>
                  </div>
                  <div className="border-t border-gray-100 my-1"></div>
                  
                  {/* eDOC */}
                  <a
                    href="https://edoc.municipio.gob.ec"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 hover:bg-gray-50 transition-colors"
                  >
                    <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-purple-100">
                      <FileText className="h-4 w-4 text-purple-600" />
                    </div>
                    <div className="flex-1">
                      <div className="font-medium text-sm text-gray-900">eDOC</div>
                      <div className="text-xs text-gray-500">Tramitología</div>
                    </div>
                    <ExternalLink className="h-3 w-3 text-gray-400" />
                  </a>

                  {/* Financiero */}
                  <a
                    href="https://financiero.municipio.gob.ec"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 hover:bg-gray-50 transition-colors"
                  >
                    <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-green-100">
                      <Briefcase className="h-4 w-4 text-green-600" />
                    </div>
                    <div className="flex-1">
                      <div className="font-medium text-sm text-gray-900">Financiero</div>
                      <div className="text-xs text-gray-500">Trytond</div>
                    </div>
                    <ExternalLink className="h-3 w-3 text-gray-400" />
                  </a>

                  {/* BI */}
                  <a
                    href="https://bi.municipio.gob.ec"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 hover:bg-gray-50 transition-colors"
                  >
                    <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-orange-100">
                      <BarChart3 className="h-4 w-4 text-orange-600" />
                    </div>
                    <div className="flex-1">
                      <div className="font-medium text-sm text-gray-900">BI</div>
                      <div className="text-xs text-gray-500">Pentaho</div>
                    </div>
                    <ExternalLink className="h-3 w-3 text-gray-400" />
                  </a>
                </div>
              </div>
            )}
          </div>

          {/* Notificaciones */}
          <button className="relative rounded-lg p-2 text-gray-700 hover:bg-gray-100 transition-colors">
            <Bell className="h-5 w-5" />
            <span className="absolute -top-1 -right-1 flex h-5 w-5 items-center justify-center rounded-full bg-red-500 text-xs font-medium text-white">
              3
            </span>
          </button>

          {/* Usuario */}
          <div className="relative">
            <button
              onClick={() => setUserMenuOpen(!userMenuOpen)}
              className="flex items-center gap-2 rounded-lg px-2 py-1 hover:bg-gray-100 transition-colors"
            >
              <div className="h-8 w-8 rounded-full bg-blue-600 flex items-center justify-center">
                <span className="text-sm font-medium text-white">JP</span>
              </div>
              <div className="hidden md:block text-left">
                <div className="text-sm font-medium text-gray-900">Juan Pérez</div>
                <div className="text-xs text-gray-500">Administrador</div>
              </div>
              <ChevronDown className="h-4 w-4 text-gray-500" />
            </button>

            {/* User Dropdown */}
            {userMenuOpen && (
              <div className="absolute right-0 mt-2 w-48 rounded-lg border border-gray-200 bg-white shadow-lg">
                <div className="p-2">
                  <button className="w-full text-left rounded-lg px-3 py-2 text-sm text-gray-700 hover:bg-gray-50">
                    Mi Perfil
                  </button>
                  <button className="w-full text-left rounded-lg px-3 py-2 text-sm text-gray-700 hover:bg-gray-50">
                    Configuración
                  </button>
                  <div className="border-t border-gray-100 my-1"></div>
                  <button className="w-full text-left rounded-lg px-3 py-2 text-sm text-red-600 hover:bg-red-50">
                    Cerrar Sesión
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
}
