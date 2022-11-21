/**
 * 
 */
package com.clinistats.hepdesk.util;

import com.clinistats.hepdesk.constatnts.ResponseStatus;
import com.clinistats.hepdesk.constatnts.ResponseStatusConstants;
import com.clinistats.hepdesk.response.GenericResponse;
import com.clinistats.hepdesk.response.GenericResponseList;
 
public interface ResponseInterface {
	
	static <T> GenericResponse<T> successResponse(T object, String message){
		
		GenericResponse<T> genericResponse = new GenericResponse<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
		genericResponse.setContent(object);
		
		return genericResponse;
	}
	
	static <T> GenericResponseList<T> successListResponse(T object, long size, String message){
		
		GenericResponseList<T> genericResponse = new GenericResponseList<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.success.getValue());
		genericResponse.setContent(object);
		genericResponse.setTotalElements(size);
		
		return genericResponse;
	}

	static <T> GenericResponse<T> dataNotFoundResponse(String message){
		
		GenericResponse<T> genericResponse = new GenericResponse<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());

		return genericResponse;
	}
	
	static <T> GenericResponseList<T> dataNotFoundListResponse(String message){
		
		GenericResponseList<T> genericResponse = new GenericResponseList<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.success.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.dataNotExists.getValue());

		return genericResponse;
	}
	
	static <T> GenericResponse<T> errorResponse(String message){
		
		GenericResponse<T> genericResponse = new GenericResponse<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.error.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
		
		return genericResponse;
	}
	
	static <T> GenericResponseList<T> errorListResponse(String message){
		
		GenericResponseList<T> genericResponse = new GenericResponseList<>();
		
		genericResponse.setMessage(message);
		genericResponse.setStatus(ResponseStatus.error.toString());
		genericResponse.setStatusCode(ResponseStatusConstants.error.getValue());
		
		return genericResponse;
	}
	

}
