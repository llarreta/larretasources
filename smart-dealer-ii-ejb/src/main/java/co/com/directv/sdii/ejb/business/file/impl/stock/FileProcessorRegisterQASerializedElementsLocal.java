/**
 * 
 */
package co.com.directv.sdii.ejb.business.file.impl.stock;

import javax.ejb.Local;

import co.com.directv.sdii.ejb.business.file.IBasicFileProcessor;

/**
 * Clase: encargada de realizar el cargue masivo de elelementos serializados a la bodega de 
 * calidad. UC INV 18
 * @author hcorredor
 *
 */
@Local
public interface FileProcessorRegisterQASerializedElementsLocal extends IBasicFileProcessor {

}
