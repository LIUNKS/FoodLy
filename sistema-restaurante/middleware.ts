import { NextResponse } from "next/server"
import type { NextRequest } from "next/server"

// Rutas que no requieren autenticación
const publicRoutes = ["/"]

// Rutas específicas por rol
const roleRoutes: Record<string, string[]> = {
  admin: ["/arqueo", "/apertura-cierre","/productos","/inventario", "/pedido", "/consolidados", "/graficos", "/empleados", "/cocina", "/logros"],
  cajero: ["/arqueo", "/apertura-cierre", "/pedido"],
  cocina: ["/pedido", "/cocina"],
}

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl

  // Permitir acceso a rutas públicas
  if (publicRoutes.includes(pathname)) {
    return NextResponse.next()
  }
  // Verificar si el usuario está autenticado
  const userStr = request.cookies.get("user")?.value

  if (!userStr) {
    // Redirigir al login si no está autenticado
    return NextResponse.redirect(new URL("/", request.url))
  }

  // Verificar token de localStorage a través de headers (si está disponible)
  const authToken = request.headers.get('authorization')?.replace('Bearer ', '');
  
  if (authToken) {
    try {
      // Decodificar y verificar expiración del token JWT
      const tokenPayload = JSON.parse(atob(authToken.split('.')[1]));
      const expirationTime = tokenPayload.exp * 1000;
      const now = Date.now();
      
      if (expirationTime <= now) {
        console.warn('🚨 Middleware: Token expirado detectado, redirigiendo al login...');
        // Token expirado, redirigir al login
        const response = NextResponse.redirect(new URL("/", request.url));
        response.cookies.delete("user");
        return response;
      }
    } catch (error) {
      console.error('Error al verificar token en middleware:', error);
    }
  }

  try {
    const user = JSON.parse(userStr)
    const role = user.role

    // Verificar si el usuario tiene acceso a la ruta solicitada
    const allowedRoutes = roleRoutes[role] || []

    // Comprobar si la ruta actual está permitida para el rol del usuario
    const hasAccess = allowedRoutes.some((route) => pathname.startsWith(route))

    if (!hasAccess && pathname !== "/logout") {
      // Redirigir a una página por defecto según el rol si no tiene acceso
      if (role === "admin") {
        return NextResponse.redirect(new URL("/pedido", request.url))
      } else if (role === "cajero") {
        return NextResponse.redirect(new URL("/pedido", request.url))
      } else if (role === "cocina") {
        return NextResponse.redirect(new URL("/cocina", request.url))
      }
    }
  } catch (error) {
    // Si hay un error al parsear el usuario, redirigir al login
    return NextResponse.redirect(new URL("/", request.url))
  }

  return NextResponse.next()
}

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
}
