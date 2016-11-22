package com.perks.emilena.dao;

import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.RotaItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class InvoiceDAO extends AbstractDAO<Invoice> {

    public InvoiceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void update(Invoice invoice) {
        persist(invoice);
    }

    public List<Invoice> listByIssueDate(LocalDate issuedDate) {
        Query query = currentSession().createQuery("select i from Invoice i where i.issuedDate = :issuedDate");
        query.setParameter("issuedDate", issuedDate);
        return list(query);
    }

    public Invoice findByRotaItem(RotaItem rotaItem) {
        Query query = currentSession().createQuery("select i from Invoice i where i.rotaItem = :rotaItem");
        query.setParameter("rotaItem", rotaItem);
        return uniqueResult(query);
    }

    public List<Invoice> fetchAll() {
        return list(currentSession().createQuery("select i from Invoice i"));
    }

}
