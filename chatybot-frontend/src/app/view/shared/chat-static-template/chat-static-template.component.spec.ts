import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatStaticTemplateComponent } from './chat-static-template.component';

describe('ChatStaticTemplateComponent', () => {
  let component: ChatStaticTemplateComponent;
  let fixture: ComponentFixture<ChatStaticTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatStaticTemplateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatStaticTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
