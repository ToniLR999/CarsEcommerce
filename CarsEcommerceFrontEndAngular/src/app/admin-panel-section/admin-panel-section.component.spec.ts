import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelSectionComponent } from './admin-panel-section.component';

describe('AdminPanelSectionComponent', () => {
  let component: AdminPanelSectionComponent;
  let fixture: ComponentFixture<AdminPanelSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPanelSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPanelSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
