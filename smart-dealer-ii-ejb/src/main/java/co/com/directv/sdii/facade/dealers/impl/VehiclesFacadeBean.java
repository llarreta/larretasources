package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.VehicleVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Vehicles 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal
 */
@Stateless(name="VehiclesFacadeBeanLocal",mappedName="ejb/VehiclesFacadeBeanLocal")
public class VehiclesFacadeBean implements VehiclesFacadeBeanLocal {

    @EJB(name="VehiclesCRUDBeanLocal",beanInterface=VehiclesCRUDBeanLocal.class)
    private VehiclesCRUDBeanLocal vehicleCrudBean;

    /**
     * Crea un vehiculo en el sistema
     * @param obj - Vehicles
     * @throws BusinessException
     */
    public void createVehicles(VehicleVO obj) throws BusinessException {
        this.vehicleCrudBean.createVehicles(obj);
    }

    /**
     * Elimina un vehiculo del sistema
     * @param obj Vehicle
     * @throws BusinessException
     */
    public void deleteVehicles(VehicleVO obj) throws BusinessException {
        this.vehicleCrudBean.deleteVehicles(obj);
    }

    /**
     * Metodo: Consultar todos los Vehiculos
     * @return List<Vehicle>
     * @throws BusinessException
     */
    public List<VehicleVO> getAllVehicles() throws BusinessException {
        return this.vehicleCrudBean.getAllVehicles();
    }

    /**
     * Obtiene un vehiculo por el id especificado
     * @param id - Long
     * @return - Vehicles
     * @throws BusinessException
     */
    public VehicleVO getVehiclesByID(Long id) throws BusinessException {
        return this.vehicleCrudBean.getVehiclesByID(id);
    }

    /**
     * Actualizar un vehiculo
     * @param obj - Vehicle
     * @throws BusinessException
     */
    public void updateVehicles(VehicleVO obj) throws BusinessException {
        this.vehicleCrudBean.updateVehicles(obj);
    }

    /**
     * Obtiene un vehiculo por la placa
     * @param id - String
     * @return - Vehicles
     * @throws BusinessException
     */
    public VehicleVO getVehicleByPlate(String plate) throws BusinessException {
        return this.vehicleCrudBean.getVehicleByPlate(plate);
    }

    public List<String> getAllVehiclePlates() throws BusinessException {
        return this.vehicleCrudBean.getAllVehiclePlates();
    }

    public List<VehicleVO> getVehiclesByDealerCodeOrDepotCode(long dealerCode, String depotCode) throws BusinessException {
        return this.vehicleCrudBean.getVehiclesByDealerCodeOrDepotCode(dealerCode, depotCode);
    }

	public List<VehicleVO> getAllVehiclesOnlyBasicInfo()
			throws BusinessException {
		return this.vehicleCrudBean.getAllVehiclesOnlyBasicInfo();
	}

	public List<VehicleVO> getVehiclesByDealerId(long dealerId)
			throws BusinessException {
		return vehicleCrudBean.getVehiclesByDealerId(dealerId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal#getActiveVehiclesByDealerId(long)
	 */
	@Override
	public List<VehicleVO> getActiveVehiclesByDealerId(long dealerId) throws BusinessException {
		return vehicleCrudBean.getActiveVehiclesByDealerId(dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal#getVehiclesByDealerIdAndPlate(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<VehicleVO> getVehiclesByDealerIdAndPlate(Long dealerId,String plate) throws BusinessException {
		return vehicleCrudBean.getVehiclesByDealerIdAndPlate(dealerId, plate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.VehiclesFacadeBeanLocal#getVehiclesByDealerIdAndPlate(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<VehicleVO> getVehiclesByDealerIdAndStatusCodeOrPlate(Long dealerId,String plate) throws BusinessException {
		return vehicleCrudBean.getVehiclesByDealerIdAndStatusCodeOrPlate(dealerId, plate);
	}
    
}
