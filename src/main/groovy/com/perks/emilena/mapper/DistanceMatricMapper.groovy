package com.perks.emilena.mapper

import com.perks.emilena.error.UnknownAddressMappingError
import com.perks.emilena.value.DistanceMatrix


/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
class DistanceMatrixMapper {

    def DistanceMatrix transformResponse(response) throws UnknownAddressMappingError{

        if(response?.status == 'OVER_QUERY_LIMIT') {
            throw new UnknownAddressMappingError('Over quota with Google Distance Matrix API');
        } else {
            try {
                def destinationAddress = response?.destination_addresses?.first()
                def originAddress = response?.origin_addresses?.first()
                def distance = response?.rows?.elements?.first()?.distance?.value?.first();
                def duration = response?.rows?.elements?.first().duration?.value?.first();

                return new DistanceMatrix(destinationAddress, originAddress, duration, distance)
            } catch (Exception e) {
                throw new UnknownAddressMappingError(e.message);
            }
        }
    }
}
