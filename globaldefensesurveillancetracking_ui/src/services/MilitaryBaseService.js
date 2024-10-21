import http from "../http-common";

class MilitaryBaseService {
  getAllMilitaryBases(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/militaryBase/militaryBases`, searchDTO);
  }

  get(militaryBaseId) {
    return this.getRequest(`/militaryBase/${militaryBaseId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/militaryBase?field=${matchData}`, null);
  }

  addMilitaryBase(data) {
    return http.post("/militaryBase/addMilitaryBase", data);
  }

  update(data) {
  	return http.post("/militaryBase/updateMilitaryBase", data);
  }
  
  uploadImage(data,militaryBaseId) {
  	return http.postForm("/militaryBase/uploadImage/"+militaryBaseId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new MilitaryBaseService();
