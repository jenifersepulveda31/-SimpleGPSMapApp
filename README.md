# SimpleGPSMapApp

## Descripción del Proyecto

Esta es una aplicación simple para Android desarrollada en Java que integra las APIs de Google Maps y el GPS del dispositivo.

El objetivo de la aplicación es:
1. Mostrar un mapa interactivo de Google Maps.
2. Solicitar y gestionar los permisos de ubicación al usuario.
3. Obtener la ubicación actual del dispositivo (simulada en el emulador).
4. Centrar la cámara del mapa en la ubicación actual y colocar un marcador.
5. Permitir al usuario añadir marcadores adicionales con una pulsación larga en el mapa.

## Pasos para Ejecutar la Aplicación

Para compilar y ejecutar esta aplicación, sigue estos pasos:

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/TU_NOMBRE_DE_USUARIO/SimpleGPSMapApp.git](https://github.com/TU_NOMBRE_DE_USUARIO/SimpleGPSMapApp.git)
    ```
2.  **Abrir en Android Studio:** Abrir la carpeta clonada como un proyecto de Android Studio.
3.  **Clave de API:** Insertar tu propia `Maps_key` en `res/values/google_maps_api.xml` y habilitarla en la Consola de Google Cloud.
4.  **Emulador:** Ejecutar la aplicación en un **Android Virtual Device (AVD)** que tenga los **Google APIs** habilitados (el icono de Google Play).
5.  **Simular Ubicación:** Una vez abierta la app, usar los "Extended Controls" del emulador para simular coordenadas GPS (Latitud y Longitud) y presionar "SET LOCATION".
