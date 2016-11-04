package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
@Entity
@Table(name = "rota")
public class Rota implements Serializable {

    @Id
    @Column(name = "rota_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
