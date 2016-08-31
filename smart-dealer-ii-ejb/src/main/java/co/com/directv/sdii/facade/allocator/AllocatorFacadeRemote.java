package co.com.directv.sdii.facade.allocator;

import javax.ejb.Remote;



/**
 * 
 * Se define esta interface para ser usada desde Quartz con el fin
 * de programar un proceso que importe las Workorders de IBS 
 * 
 * Fecha de Creación: 10/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal
 */
@Remote
public interface AllocatorFacadeRemote extends AllocatorFacadeLocal {

}
