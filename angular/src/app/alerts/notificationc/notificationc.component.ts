import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-notificationc',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notificationc.component.html',
  styleUrl: './notificationc.component.css'
})
export class NotificationcComponent implements OnInit{
  visible = false;
  message = '';

  ngOnInit() {}

  show(message: string) {
    this.message = message;
    this.visible = true;
    setTimeout(() => {
      this.visible = false;
    }, 3000); // Ocultar la notificación después de 3 segundos
  }
}
