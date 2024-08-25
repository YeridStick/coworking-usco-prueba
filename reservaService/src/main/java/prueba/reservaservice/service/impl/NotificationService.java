package prueba.reservaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import prueba.reservaservice.entity.ReservaEntity;
import prueba.reservaservice.service.INotificacionesServices;

@Service
public class NotificationService implements INotificacionesServices {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envía una actualización de la reserva a todos los clientes suscritos al tema "/topic/reservas".
     *
     * @param reserva La reserva que se ha creado o actualizado.
     */
    @Override
    public void sendReservationUpdate(ReservaEntity reserva) {
        messagingTemplate.convertAndSend("/topic/reservas", reserva);
    }
}