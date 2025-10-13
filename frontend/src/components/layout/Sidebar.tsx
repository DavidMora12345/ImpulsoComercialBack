"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils/cn";
import {
  Home,
  Users,
  Building2,
  DollarSign,
  Settings,
  HelpCircle,
  ChevronRight,
  Search,
  UserPlus,
  GitMerge,
  MapPin,
  Mountain,
  Calculator,
  Map,
  FileText,
  CreditCard,
  Handshake,
  Droplet,
  BarChart,
} from "lucide-react";
import { useState } from "react";

interface SidebarProps {
  isOpen: boolean;
}

const menuItems = [
  {
    title: "Dashboard",
    icon: Home,
    href: "/dashboard",
  },
  {
    section: "CLIENTES",
    items: [
      {
        title: "Clientes",
        icon: Users,
        href: "/dashboard/clientes",
      },
      {
        title: "Buscar Cliente",
        icon: Search,
        href: "/dashboard/clientes/buscar",
        indent: true,
      },
      {
        title: "Nuevo Cliente",
        icon: UserPlus,
        href: "/dashboard/clientes/nuevo",
        indent: true,
      },
      {
        title: "Unificar Clientes",
        icon: GitMerge,
        href: "/dashboard/clientes/unificar",
        indent: true,
      },
    ],
  },
  {
    section: "CATASTROS",
    items: [
      {
        title: "Predios Urbanos",
        icon: Building2,
        href: "/dashboard/catastros/urbanos",
      },
      {
        title: "Predios Rurales",
        icon: Mountain,
        href: "/dashboard/catastros/rurales",
      },
      {
        title: "Avalúos",
        icon: Calculator,
        href: "/dashboard/catastros/avaluos",
      },
      {
        title: "Mapa GIS",
        icon: Map,
        href: "/dashboard/catastros/mapa",
      },
    ],
  },
  {
    section: "RECAUDACIÓN",
    items: [
      {
        title: "Facturas",
        icon: FileText,
        href: "/dashboard/recaudacion/facturas",
      },
      {
        title: "Pagos",
        icon: CreditCard,
        href: "/dashboard/recaudacion/pagos",
      },
      {
        title: "Convenios de Pago",
        icon: Handshake,
        href: "/dashboard/recaudacion/convenios",
      },
      {
        title: "Emisión Agua",
        icon: Droplet,
        href: "/dashboard/recaudacion/emision-agua",
      },
      {
        title: "Reportes",
        icon: BarChart,
        href: "/dashboard/recaudacion/reportes",
      },
    ],
  },
];

export function Sidebar({ isOpen }: SidebarProps) {
  const pathname = usePathname();
  const [collapsed, setCollapsed] = useState(false);

  return (
    <aside
      className={cn(
        "sticky top-16 h-[calc(100vh-4rem)] border-r bg-white transition-all",
        collapsed ? "w-16" : "w-64"
      )}
    >
      <div className="flex h-full flex-col">
        
        {/* Toggle Button */}
        <div className="flex h-14 items-center justify-between px-4 border-b">
          {!collapsed && (
            <span className="text-sm font-semibold text-gray-700">MENÚ</span>
          )}
          <button
            onClick={() => setCollapsed(!collapsed)}
            className="rounded-lg p-1.5 hover:bg-gray-100 transition-colors"
          >
            <ChevronRight className={cn(
              "h-4 w-4 text-gray-600 transition-transform",
              collapsed ? "" : "rotate-180"
            )} />
          </button>
        </div>

        {/* Navigation */}
        <nav className="flex-1 overflow-y-auto p-3 space-y-1">
          
          {menuItems.map((menuItem, index) => {
            // Si es un item simple (Dashboard)
            if ('href' in menuItem && menuItem.href) {
              const Icon = menuItem.icon;
              const isActive = pathname === menuItem.href;
              
              return (
                <Link
                  key={menuItem.href}
                  href={menuItem.href}
                  className={cn(
                    "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
                    isActive 
                      ? "bg-blue-50 text-blue-600" 
                      : "text-gray-700 hover:bg-gray-100",
                    collapsed && "justify-center"
                  )}
                >
                  <Icon className="h-5 w-5 flex-shrink-0" />
                  {!collapsed && <span className="flex-1">{menuItem.title}</span>}
                </Link>
              );
            }
            
            // Si es una sección con items
            if ('section' in menuItem && menuItem.items) {
              return (
                <div key={index}>
                  {/* Section Title */}
                  {!collapsed && (
                    <div className="mt-4 mb-2 px-3">
                      <span className="text-xs font-semibold text-gray-500">
                        {menuItem.section}
                      </span>
                    </div>
                  )}
                  
                  {/* Section Items */}
                  {menuItem.items.map((item) => {
                    const Icon = item.icon;
                    const isActive = pathname === item.href;
                    
                    return (
                      <Link
                        key={item.href}
                        href={item.href}
                        className={cn(
                          "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
                          item.indent && !collapsed && "ml-6",
                          isActive 
                            ? "bg-blue-50 text-blue-600" 
                            : "text-gray-700 hover:bg-gray-100",
                          collapsed && "justify-center"
                        )}
                      >
                        <Icon className="h-5 w-5 flex-shrink-0" />
                        {!collapsed && <span className="flex-1">{item.title}</span>}
                      </Link>
                    );
                  })}
                </div>
              );
            }
            
            return null;
          })}

          {/* Separador */}
          <div className="my-3 border-t" />

          {/* Administración */}
          <Link
            href="/dashboard/admin"
            className={cn(
              "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
              pathname === "/dashboard/admin"
                ? "bg-blue-50 text-blue-600" 
                : "text-gray-700 hover:bg-gray-100",
              collapsed && "justify-center"
            )}
          >
            <Settings className="h-5 w-5 flex-shrink-0" />
            {!collapsed && <span className="flex-1">Administración</span>}
          </Link>

          {/* Ayuda */}
          <Link
            href="/dashboard/ayuda"
            className={cn(
              "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
              pathname === "/dashboard/ayuda"
                ? "bg-blue-50 text-blue-600" 
                : "text-gray-700 hover:bg-gray-100",
              collapsed && "justify-center"
            )}
          >
            <HelpCircle className="h-5 w-5 flex-shrink-0" />
            {!collapsed && <span className="flex-1">Ayuda</span>}
          </Link>
        </nav>
      </div>
    </aside>
  );
}
