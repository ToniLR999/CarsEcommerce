import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-admin-panel-section',
  templateUrl: './admin-panel-section.component.html',
  styleUrl: './admin-panel-section.component.css'
})
export class AdminPanelSectionComponent {


  @ViewChild('createCarFormContainer') addCarFormContainer!: ElementRef;
  createCarForm!: UntypedFormGroup;

  @Output() formSubmit = new EventEmitter<any>();
  @Output() closeForm = new EventEmitter<void>();
  
  onSubmit(){

    this.formSubmit.emit(this.createCarForm.value); // Emitimos los datos del formulario
    this.createCarForm.reset(); // Limpiamos el formulario después de enviar

  }


  onClose(): void {
    this.closeForm.emit(); // Emitimos un evento para cerrar el formulario
  }
  
}
