"use client";

import Link from "next/link";
import { useState, useEffect } from "react";
import { usePathname } from "next/navigation";
import { useBreakpoints } from "@/hooks/useBreakpoints";

interface SidebarProps {
  isOpen: boolean;
  toggleSidebar: () => void;
}

export default function Sidebar({ isOpen, toggleSidebar }: SidebarProps) {
  const pathname = usePathname(); // Obtener la ruta actual
  const [openSubmenus, setOpenSubmenus] = useState<{ [key: string]: boolean }>(
    {}
  );
  const { isMobile } = useBreakpoints();

  const toggleSubmenu = (name: string) => {
    setOpenSubmenus((prev) => ({
      ...prev,
      [name]: !prev[name],
    }));
  };

  // Función para verificar si una ruta está activa
  const isActive = (href: string) => pathname === href;

  // Función para verificar si algún enlace del submenu está activo
  const isSubmenuActive = (hrefs: string[]) => hrefs.some((href) => isActive(href));  // Cierra el sidebar al cambiar de ruta en dispositivos móviles
  useEffect(() => {
    const handleRouteChange = () => {
      if (isMobile && isOpen) {
        setTimeout(() => {
          toggleSidebar();
        }, 300); // Pequeño retraso para evitar problemas
      }
    };
    
    if (pathname) {
      handleRouteChange();
    }
  }, [pathname]);

  // Log para debugging
  useEffect(() => {
    console.log("Sidebar isOpen state:", isOpen);
  }, [isOpen]);
  return (
    <div id="sidebar" className={isOpen ? "open" : ""}>
      {/* Enlace de marca */}
      <div className="brand-container">
        <Link href="/" className="brand-link">
          <i className="fa-2x fas fa-utensils"></i>
          <span>FoodLy</span>
        </Link>
        {isMobile && (
          <button 
            className="close-sidebar-btn" 
            onClick={() => {
              console.log("Close sidebar button clicked");
              toggleSidebar();
            }}
          >
            <i className="fas fa-times"></i>
          </button>
        )}
      </div>

      <ul>
        {/* Menú: Caja */}
        <li>
          <a
            href="#"
            className={isSubmenuActive(["/arqueo", "/apertura-cierre"]) ? "active" : ""}
            onClick={(e) => {
              e.preventDefault();
              toggleSubmenu("Caja");
            }}
          >
            <i className="fas fa-th"></i>Caja
            <i className="fas fa-chevron-down toggle-icon"></i>
          </a>
          <ul
            className="submenu"
            style={{ display: openSubmenus["Caja"] ? "block" : "none" }}
          >
            <li>
              <Link href="/arqueo" className={isActive("/arqueo") ? "active" : ""}>
                <i className="fa-regular fa-clipboard"></i> Arqueo
              </Link>
            </li>
            <li>
              <Link href="/apertura-cierre" className={isActive("/apertura-cierre") ? "active" : ""}>
                <i className="fa-solid fa-box"></i> Apertura y cierre
              </Link>
            </li>
          </ul>
        </li>

        {/* Menú: Pedido */}
        <li>
          <Link href="/pedido" className={isActive("/pedido") ? "active" : ""}>
            <i className="fa-solid fa-cart-shopping"></i> Pedido
          </Link>
        </li>

        {/* Menú: Reportes */}
        <li>
          <a
            href="#"
            className={isSubmenuActive(["/consolidados", "/graficos"]) ? "active" : ""}
            onClick={(e) => {
              e.preventDefault();
              toggleSubmenu("Reportes");
            }}
          >
            <i className="fa-regular fa-file-lines"></i>Reportes
            <i className="fas fa-chevron-down toggle-icon"></i>
          </a>
          <ul
            className="submenu"
            style={{ display: openSubmenus["Reportes"] ? "block" : "none" }}
          >
            <li>
              <Link href="/consolidados" className={isActive("/consolidados") ? "active" : ""}>
                <i className="fa-regular fa-chart-bar"></i> Consolidados
              </Link>
            </li>
            <li>
              <Link href="/graficos" className={isActive("/graficos") ? "active" : ""}>
                <i className="fa-solid fa-chart-pie"></i>Gráficos
              </Link>
            </li>
          </ul>
        </li>

        {/* Menú: Productos */}
        <li>
          <a
            href="#"
            className={isSubmenuActive(["/productos", "/inventario"]) ? "active" : ""}
            onClick={(e) => {
              e.preventDefault();
              toggleSubmenu("Productos");
            }}
          >
            <i className="fa-regular fa-file-lines"></i>Productos
            <i className="fas fa-chevron-down toggle-icon"></i>
          </a>
          <ul
            className="submenu"
            style={{ display: openSubmenus["Productos"] ? "block" : "none" }}
          >
            <li>
              <Link href="/productos" className={isActive("/productos") ? "active" : ""}>
                <i className="fa-regular fa-chart-bar"></i> Productos
              </Link>
            </li>
            <li>
              <Link href="/inventario" className={isActive("/inventario") ? "active" : ""}>
                <i className="fa-solid fa-chart-pie"></i>Inventario
              </Link>
            </li>
          </ul>
        </li>

        {/* Menú: Empleados */}
        <li>
          <Link href="/empleados" className={isActive("/empleados") ? "active" : ""}>
            <i className="fa-solid fa-users"></i> Empleados
          </Link>
        </li>

        {/* Menú: Cocina */}
        <li>
          <Link href="/cocina" className={isActive("/cocina") ? "active" : ""}>
            <i className="fa-solid fa-utensils"></i> Cocina
          </Link>
        </li>

        {/* Menú: Logros */}
        <li>
          <Link href="/logros" className={isActive("/logros") ? "active" : ""}>
            <i className="fa-solid fa-house"></i> Logros
          </Link>
        </li>

        {/* Menú: Salir */}
        <li>
          <Link href="/" className={isActive("/") ? "active" : ""}>
            <i className="fa-solid fa-right-from-bracket"></i> Salir
          </Link>
        </li>
      </ul>
    </div>
  );
}
