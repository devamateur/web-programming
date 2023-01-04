import { API_BASE_URL } from "../app-config";
const ACCESS_TOKEN = "ACCESS_TOKEN";

export function call(api, method, request){
    let headers = new Headers({
        "Content-Type":"application/json",
    });

    // 로컬 스토리지에서 ACCESS_TOKEN 가져오기
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if(accessToken && accessToken !== null)
        headers.append("Authorization", "Bearer "+accessToken); // 헤더에 추가

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };
    
    // 요청 데이터가 있다면
    if (request){
        // GET
        options.body = JSON.stringify(request);
    }

    // Promise 객체를 반환
    return fetch(options.url, options).then((response) =>
        response.json().then((json) => {
            if(!response.ok){
                return Promise.reject(json);
            }
            return json;    
        })
    )
    .catch((error) => {
        console.log(error.status);
        if(error.status === 403){  // 로그인 실패 시
            window.location.href="/login";   // redirect
        }
        return Promise.reject(error);
    });
}

// 로그인 요청 함수
export function signin(bookUserDTO){
    return call("/bookauth/signin", "POST", bookUserDTO)
    .then((response) => {
        if(response.token) {   // 토큰이 존재하는 경우(로그인된 경우)
            // 로컬 스토리지에 토큰 저장
            localStorage.setItem("ACCESS_TOKEN", response.token);
            window.location.href="/";    // book화면으로 리다이렉트
        }
    })
}

// 로그아웃
export function signout(){
    localStorage.setItem(ACCESS_TOKEN, null);
    window.location.href="/login";
}

// 계정 생성
export function signup(bookUserDTO){
    return call("/bookauth/signup", "POST", bookUserDTO);
}