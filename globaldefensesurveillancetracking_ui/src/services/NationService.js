import http from "../http-common";

class NationService {
  getAllNations(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/nation/nations`, searchDTO);
  }

  get(nationId) {
    return this.getRequest(`/nation/${nationId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/nation?field=${matchData}`, null);
  }

  addNation(data) {
    return http.post("/nation/addNation", data);
  }

  update(data) {
  	return http.post("/nation/updateNation", data);
  }
  
  uploadImage(data,nationId) {
  	return http.postForm("/nation/uploadImage/"+nationId, data);
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

export default new NationService();
