package com.movieticket.booking.service;

import com.movieticket.booking.entity.Booking;
import com.movieticket.booking.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Welcome to Movie Ticket Booking System!");
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Welcome to Movie Ticket Booking System!\n\n" +
                "Your account has been created successfully.\n" +
                "Email: %s\n\n" +
                "Start booking your favorite movies now!\n\n" +
                "Best regards,\nMovie Ticket Booking Team",
                user.getName(), user.getEmail()
            ));
            mailSender.send(message);
            log.info("Welcome email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", user.getEmail(), e.getMessage());
        }
    }

    @Async
    public void sendBookingConfirmationEmail(User user, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Booking Confirmation - " + booking.getBookingId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Your booking has been confirmed!\n\n" +
                "Booking ID: %s\n" +
                "Total Amount: Rs. %.2f\n" +
                "Status: %s\n\n" +
                "Thank you for booking with us!\n\n" +
                "Best regards,\nMovie Ticket Booking Team",
                user.getName(), booking.getBookingId(),
                booking.getTotalAmount(), booking.getStatus()
            ));
            mailSender.send(message);
            log.info("Booking confirmation email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send booking confirmation email: {}", e.getMessage());
        }
    }

    @Async
    public void sendBookingCancellationEmail(User user, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Booking Cancelled - " + booking.getBookingId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Your booking has been cancelled.\n\n" +
                "Booking ID: %s\n" +
                "Refund Amount: Rs. %.2f\n\n" +
                "Refund will be processed in 5-7 business days.\n\n" +
                "Best regards,\nMovie Ticket Booking Team",
                user.getName(), booking.getBookingId(), booking.getTotalAmount()
            ));
            mailSender.send(message);
            log.info("Booking cancellation email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send cancellation email: {}", e.getMessage());
        }
    }

    @Async
    public void sendPaymentSuccessEmail(User user, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Payment Successful - " + booking.getBookingId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Payment received successfully!\n\n" +
                "Booking ID: %s\n" +
                "Amount Paid: Rs. %.2f\n" +
                "Transaction ID: %s\n\n" +
                "Enjoy your movie!\n\n" +
                "Best regards,\nMovie Ticket Booking Team",
                user.getName(), booking.getBookingId(),
                booking.getTotalAmount(),
                booking.getPayment() != null ? booking.getPayment().getTransactionId() : "N/A"
            ));
            mailSender.send(message);
            log.info("Payment success email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send payment success email: {}", e.getMessage());
        }
    }
}
