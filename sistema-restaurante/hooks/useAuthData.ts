import { useContext } from "react";
import { AuthContext } from "@/context/AuthContext";
import { getAuthToken, getUserRole, getUserId } from "@/services/auth-service";
import { hasAccess, getAccessibleModules, getDefaultRoute, isValidRole, getRoleName } from "@/utils/permissions";

/**
 * Hook personalizado para acceder a los datos de autenticación
 * Incluye tanto el estado del contexto como acceso directo al localStorage
 */
export const useAuthData = () => {
  const context = useContext(AuthContext);
  
  if (!context) {
    throw new Error("useAuthData debe ser usado dentro de un AuthProvider");
  }

  // Funciones de utilidad para acceder a datos específicos
  const getStoredToken = () => getAuthToken();
  const getStoredRole = () => getUserRole();
  const getStoredUserId = () => getUserId();
  
  // Datos del usuario desde el contexto
  const { user, token, isLoading, error } = context;
    // Verificar si el usuario tiene un rol específico
  const hasRole = (role: string): boolean => {
    return user?.role === role || getStoredRole() === role;
  };
  
  // Verificar si el usuario está autenticado
  const isAuthenticated = (): boolean => {
    return !!(user && token) || !!getStoredToken();
  };
  
  // Verificar si el usuario tiene acceso a un módulo específico
  const canAccess = (module: string): boolean => {
    const userRole = user?.role || getStoredRole();
    return userRole ? hasAccess(userRole, module) : false;
  };
  
  // Obtener todos los módulos accesibles para el usuario
  const getAccessibleRoutes = () => {
    const userRole = user?.role || getStoredRole();
    return userRole ? getAccessibleModules(userRole) : [];
  };
  
  // Obtener la ruta por defecto para el usuario
  const getUserDefaultRoute = (): string => {
    const userRole = user?.role || getStoredRole();
    return userRole ? getDefaultRoute(userRole) : '/';
  };
  
  // Verificar si el rol del usuario es válido
  const hasValidRole = (): boolean => {
    const userRole = user?.role || getStoredRole();
    return userRole ? isValidRole(userRole) : false;
  };
  
  // Obtener el nombre amigable del rol
  const getUserRoleName = (): string => {
    const userRole = user?.role || getStoredRole();
    return userRole ? getRoleName(userRole) : 'Sin rol';
  };
  
  // Obtener información específica del usuario
  const getUserInfo = () => {
    if (!user) return null;
    
    return {
      id: user.id,
      name: user.name,
      email: user.email,
      dni: user.dni,
      role: user.role,
    };
  };
  return {
    // Estado del contexto
    user,
    token,
    isLoading,
    error,
    
    // Funciones de utilidad
    hasRole,
    isAuthenticated,
    getUserInfo,
    
    // Funciones de control de acceso
    canAccess,
    getAccessibleRoutes,
    getUserDefaultRoute,
    hasValidRole,
    getUserRoleName,
    
    // Acceso directo al localStorage (útil cuando el contexto no está disponible)
    getStoredToken,
    getStoredRole,
    getStoredUserId,
    
    // Métodos del contexto
    login: context.login,
    logout: context.logout,
  };
};
