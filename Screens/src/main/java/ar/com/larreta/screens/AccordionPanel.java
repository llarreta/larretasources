package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "accordionPanel")
@DiscriminatorValue(value = "accordionPanel")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class AccordionPanel extends StandardContainer {

}
