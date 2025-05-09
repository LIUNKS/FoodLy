"use client";

import Link from "next/link";
import { useState } from "react";

export default function Sidebar() {
  const [openSubmenus, setOpenSubmenus] = useState<{ [key: string]: boolean }>(
    {}
  );

  const toggleSubmenu = (name: string) => {
    setOpenSubmenus((prev) => ({
      ...prev,
      [name]: !prev[name],
    }));
  };

  return (
    <div id="sidebar">
      <Link href="/" className="brand-link">
        <i className="fa-2x fas fa-utensils"></i>
        <span>FoodLy</span>
      </Link>

      <ul>
        <li>
          <a
            href="#"
            className="active"
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
              <Link href="/arqueo" className="active">
                <i className="fa-regular fa-clipboard"></i> Arqueo
              </Link>
            </li>
            <li>
              <Link href="/apertura-cierre">
                <i className="fa-solid fa-box"></i> Apertura y cierre
              </Link>
            </li>
          </ul>
        </li>
        <li>
          <Link href="/pedido">
            <i className="fa-solid fa-cart-shopping"></i> Pedido
          </Link>
        </li>
        <li>
          <a
            href="#"
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
              <Link href="/consolidados">
                <i className="fa-regular fa-chart-bar"></i> Consolidados
              </Link>
            </li>
            <li>
              <Link href="/graficos">
                <i className="fa-solid fa-chart-pie"></i>Gráficos
              </Link>
            </li>
          </ul>
        </li>
        <li>
          <a
            href="#"
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
              <Link href="/productos">
                <i className="fa-regular fa-chart-bar"></i> Poroductos
              </Link>
            </li>
            <li>
              <Link href="/inventario">
                <i className="fa-solid fa-chart-pie"></i>Inventario
              </Link>
            </li>
          </ul>
        </li>
        <li>
          <Link href="/empleados">
            <i className="fa-solid fa-users"></i> Empleados
          </Link>
        </li>
        <li>
          <Link href="/cocina">
            <i className="fa-solid fa-utensils"></i> Cocina
          </Link>
        </li>
        <li>
          <Link href="/logros">
            <i className="fa-solid fa-house"></i> Logros
          </Link>
        </li>
        <li>
          <Link href="/">
            <i className="fa-solid fa-right-from-bracket"></i> Salir
          </Link>
        </li>
      </ul>
    </div>
  );
}
