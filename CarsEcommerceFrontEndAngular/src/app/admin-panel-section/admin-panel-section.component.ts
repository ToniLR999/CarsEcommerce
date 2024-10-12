import { Component, ElementRef, ViewChild } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-panel-section',
  templateUrl: './admin-panel-section.component.html',
  styleUrl: './admin-panel-section.component.css'
})
export class AdminPanelSectionComponent {

  @ViewChild('createCarFormContainer') addCarFormContainer!: ElementRef;
  createCarForm!: UntypedFormGroup;


  paginatedRows: string[][] = []; // Filas mostradas en la página actual
  rows: string[][] = []; // Array de filas de la tabla
  rowsPerPage: number = 5; // Número de filas por página
  currentPage: number = 1; // Página actual
  pages: number[] = []; // Número de páginas
  maxVisiblePages: number = 5; // Máximo número de botones de página visibles
  
  
  
  getTableData(): string[][] {
    // Añadimos más filas para probar la paginación
    return Array.from({ length: 50 }, (_, i) => [
      `Content ${i + 1}`,
      `Content ${i + 1}`,
      `Content ${i + 1}`,
      `Content ${i + 1}`,
      `Content ${i + 1}`
    ]);
  }
  createCar(){



  }
  
  displayTable(page: number): void {
    this.currentPage = page;
    const startIndex = (page - 1) * this.rowsPerPage;
    const endIndex = startIndex + this.rowsPerPage;
    this.paginatedRows = this.rows.slice(startIndex, endIndex);
  }
  
  
  calculatePages(): void {
    const totalPages = Math.ceil(this.rows.length / this.rowsPerPage);
    this.pages = Array.from({ length: totalPages }, (_, i) => i + 1);
  }
  
  changePage(page: number): void {
    this.displayTable(page);
  }
  
  previousPage(): void {
    if (this.currentPage > 1) {
      this.changePage(this.currentPage - 1);
    }
  }
  
  nextPage(): void {
    if (this.currentPage < this.pages.length) {
      this.changePage(this.currentPage + 1);
    }
  }
  
  // Método para obtener las páginas visibles en la paginación
  getVisiblePages(): number[] {
    const halfRange = Math.floor(this.maxVisiblePages / 2);
    let start = Math.max(this.currentPage - halfRange, 1);
    let end = Math.min(start + this.maxVisiblePages - 1, this.pages.length);
  
    if (end - start < this.maxVisiblePages - 1) {
      start = Math.max(end - this.maxVisiblePages + 1, 1);
    }
  
    return this.pages.slice(start - 1, end);
  }
  
}
