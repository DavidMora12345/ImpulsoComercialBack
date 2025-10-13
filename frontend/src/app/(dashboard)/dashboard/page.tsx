"use client";

import { Users, MapPin, Receipt, TrendingUp } from "lucide-react";

export default function DashboardPage() {
  const stats = [
    {
      title: "Total Clientes",
      value: "15,234",
      icon: Users,
      color: "bg-blue-500",
      change: "+12%",
    },
    {
      title: "Predios Registrados",
      value: "8,456",
      icon: MapPin,
      color: "bg-green-500",
      change: "+5%",
    },
    {
      title: "Facturas Pendientes",
      value: "1,234",
      icon: Receipt,
      color: "bg-yellow-500",
      change: "-8%",
    },
    {
      title: "Recaudación Mensual",
      value: "$125,430",
      icon: TrendingUp,
      color: "bg-purple-500",
      change: "+18%",
    },
  ];

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Dashboard</h1>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {stats.map((stat) => (
          <div
            key={stat.title}
            className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition-shadow"
          >
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600 mb-1">{stat.title}</p>
                <p className="text-2xl font-bold text-gray-900">{stat.value}</p>
                <p className="text-sm text-green-600 mt-1">{stat.change} vs mes anterior</p>
              </div>
              <div className={`${stat.color} p-3 rounded-lg`}>
                <stat.icon className="h-6 w-6 text-white" />
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Últimas Facturas */}
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-900 mb-4">
            Últimas Facturas
          </h2>
          <div className="space-y-3">
            {[1, 2, 3, 4, 5].map((i) => (
              <div
                key={i}
                className="flex items-center justify-between py-2 border-b border-gray-100"
              >
                <div>
                  <p className="font-medium text-gray-900">FAC-{1000 + i}</p>
                  <p className="text-sm text-gray-600">Cliente #{i}</p>
                </div>
                <div className="text-right">
                  <p className="font-medium text-gray-900">${(Math.random() * 1000).toFixed(2)}</p>
                  <span className="text-xs bg-yellow-100 text-yellow-800 px-2 py-1 rounded">
                    Pendiente
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Últimos Pagos */}
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-900 mb-4">
            Últimos Pagos
          </h2>
          <div className="space-y-3">
            {[1, 2, 3, 4, 5].map((i) => (
              <div
                key={i}
                className="flex items-center justify-between py-2 border-b border-gray-100"
              >
                <div>
                  <p className="font-medium text-gray-900">REC-{2000 + i}</p>
                  <p className="text-sm text-gray-600">Cliente #{i}</p>
                </div>
                <div className="text-right">
                  <p className="font-medium text-gray-900">${(Math.random() * 500).toFixed(2)}</p>
                  <span className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded">
                    Pagado
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
