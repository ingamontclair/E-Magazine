import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleslistComponent } from './articleslist.component';

describe('ArticleslistComponent', () => {
  let component: ArticleslistComponent;
  let fixture: ComponentFixture<ArticleslistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleslistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
