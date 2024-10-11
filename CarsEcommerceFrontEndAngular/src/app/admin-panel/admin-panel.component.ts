import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.css'
})
export class AdminPanelComponent {
 // Método para mostrar la sección seleccionada
 mostrarSeccion(seccion: string): void {
  // Ocultar todas las secciones
  const secciones = document.querySelectorAll('.seccion') as NodeListOf<HTMLElement>;
  secciones.forEach((sec) => {
    sec.style.display = 'none';
  });

  // Mostrar la sección seleccionada
  const selectedSection = document.getElementById(seccion);
  if (selectedSection) {
    selectedSection.style.display = 'block';
  }
}

}
