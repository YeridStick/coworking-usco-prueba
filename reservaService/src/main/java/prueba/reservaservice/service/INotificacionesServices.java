package prueba.reservaservice.service;

import prueba.reservaservice.entity.ReservaEntity;

public interface INotificacionesServices {
    void sendReservationUpdate(ReservaEntity reserva);
}
