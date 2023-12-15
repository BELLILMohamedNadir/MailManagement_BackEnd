package com.example.MailManagementApi.model;


import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mail {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "structure_id")
        private Structure structure;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;


        private String receivedCategory;

        private Date entryDate,departureDate,entryDateReceived,mailDate;

        @NotNull
        private String forStructure,internalReference,mailReference,objectReceived,object
                ,recipient,priority,type,responseOf;


        @NotNull
        private boolean response,classed;

        public Mail(Long id) {
                this.id = id;
        }

        public Mail(Structure structure, Category category, String forStructure, Date entryDate, Date departureDate, Date mailDate, String internalReference, String object, String recipient, String priority, String type, String responseOf, boolean response, boolean classed) {
                this.structure = structure;
                this.category = category;
                this.forStructure = forStructure;
                this.entryDate = entryDate;
                this.departureDate = departureDate;
                this.mailDate = mailDate;
                this.internalReference = internalReference;
                this.object = object;
                this.recipient = recipient;
                this.priority = priority;
                this.type = type;
                this.responseOf = responseOf;
                this.response = response;
                this.classed = classed;
        }
        public Mail(Structure structure, Category category, String receivedCategory, String forStructure, Date entryDate, Date departureDate,Date entryDateReceived, Date mailDate, String internalReference, String mailReference, String objectReceived, String object, String recipient, String priority, String type, String responseOf, boolean response, boolean classed) {
                this.structure = structure;
                this.category = category;
                this.receivedCategory = receivedCategory;
                this.forStructure = forStructure;
                this.entryDate = entryDate;
                this.departureDate = departureDate;
                this.mailDate = mailDate;
                this.internalReference = internalReference;
                this.mailReference = mailReference;
                this.entryDateReceived = entryDateReceived;
                this.objectReceived = objectReceived;
                this.object = object;
                this.recipient = recipient;
                this.priority = priority;
                this.type = type;
                this.responseOf = responseOf;
                this.response = response;
                this.classed = classed;
        }
}
