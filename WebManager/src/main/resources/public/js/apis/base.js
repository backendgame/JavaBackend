"use-strict"

import {BASE_URL, TOKEN_NAME} from '../CONFIG.js';

let headers = {
  'Content-type': 'application/json',
  'cache-control': 'no-cache' 
}

function getFullApiPath(path) {
  return BASE_URL + path; 
}

function getToken() {
  let token = localStorage.getItem(TOKEN_NAME);
  if(token) {
      return token;
  }
  return null;
}

const httpRequest = {
  get: async function(endpoint, params, requiredToken = true) {
    let requestStatus = true;
    let response;
    try {
      let paramsString = "";
      if(params && params.length > 0) {
        paramsString = params.map(item => `${item.key || ""}=${item.value || ""}`).join(",");
      }
      const fullPathApi = getFullApiPath(endpoint) + paramsString;
      if(requiredToken) {
        headers = {...headers, [`${TOKEN_NAME}`]: getToken()}
      }
    
      response = await fetch(fullPathApi, {
        method: 'GET',
        headers
      })
      .then(response => {
        if(response.ok) {
          return response.json()
        }
        requestStatus = false;
        return {
          status: response.status, message: response.statusText
        }
      })
      .then(result => {
          return result;
      })
      .catch(err => {
        requestStatus = false;
        return {error: true}
      });
    
    }
    catch(e) {
      requestStatus = false;
      console.log("Error: ", e)
    }
    finally {
      if(response.status != "Success") {
        requestStatus = false;
      }
      return {
        ...response,
        requestStatus 
      }
    }
  },
  post: async function(endpoint, data, requiredToken = true) {
    let requestStatus = true;
    let response;
    try {
      let _data = {};
      if(data) {
        _data = data;
      }
      const fullPathApi = getFullApiPath(endpoint);
      if(requiredToken) {
        headers = {...headers, [`${TOKEN_NAME}`]: getToken()}
      }
    
      response = await fetch(fullPathApi, {
        method: 'POST',
        headers,
        body: JSON.stringify(_data),
      })
      .then(response => {
        if(response.ok) {
          return response.json();
        }
        requestStatus = false;
        return {
          status: response.status, message: response.statusText
        }
      })
      .then(result => {
          return result;
      })
      .catch(err => {
        requestStatus = false;
        return {error: true}
      });
    
      return response;
    }
    catch(e) {
      requestStatus = false;
      console.log("Error: ", e)
    }
    finally {
      if(response.status != "Success") {
        requestStatus = false;
      }
      return {
        ...response,
        requestStatus 
      }
    }
  },
  put: async function(endpoint, data, requiredToken = true) {
    let requestStatus = true;
    let response;
    try {
      let _data = {};
      if(data) {
        _data = data;
      }
      const fullPathApi = getFullApiPath(endpoint);
      if(requiredToken) {
        headers = {...headers, [`${TOKEN_NAME}`]: getToken()}
      }
    
      response = await fetch(fullPathApi, {
        method: 'PUT',
        headers,
        body: JSON.stringify(_data),
      })
      .then(response => {
        if(response.ok) {
          return response.json();
        }
        requestStatus = false;
        return {
          status: response.status, message: response.statusText
        }
      })
      .then(result => {
          return result;
      })
      .catch(err => {
        requestStatus = false;
        return {error: true}
      });
    
      return response;
    }
    catch(e) {
      requestStatus = false;
      console.log("Error: ", e)
    }
    finally {
      if(response.status != "Success") {
        requestStatus = false;
      }
      return {
        ...response,
        requestStatus 
      }
    }
  },
  del: async function(endpoint, params, requiredtoken = true) {
    let requestStatus = true;
    let response;
    try {
      let _data = {};
      if(data) {
        _data = data;
      }
      const fullPathApi = getFullApiPath(endpoint);
      if(requiredtoken) {
        headers = {...headers, [`${token_name}`]: gettoken()}
      }
    
      response = await fetch(fullpathapi, {
        method: 'delete',
        headers,
        body: json.stringify(_data),
      })
      .then(response => {
        if(response.ok) {
          return response.json();
        }
        requestStatus = false;
        return {
          status: response.status, message: response.statusText
        }
      })
      .then(result => {
          return result;
      })
      .catch(err => {
        requestStatus = false;
        return {error: true}
      });
    
      return response;
    }
    catch(e) {
      requestStatus = false;
      console.log("Error: ", e) 
    }
    finally {
      if(response.status != "Success") {
        requestStatus = false;
      }
      return {
        ...response,
        requestStatus 
      }
    }
  }
}

export {
  getFullApiPath,
  httpRequest
}