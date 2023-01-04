import React from "react";
import { Button, TextField, Link, Grid, Container, Typography}
    from "@material-ui/core";
import {signup} from "./service/ApiService";

class SignUp extends React.Component{
    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event){
        event.preventDefault();

        // 오브젝트에서 form에 저장된 데이터를 맵 형태로 변환
        const data = new FormData(event.target);
        const username = data.get("username");
        const email = data.get("email");
        const password = data.get("password");
        signup({email: email, username: username, password:password}).then(
            (response) => {
                // 계정 생성 성공 시 login 페이지로 리다이렉트
                window.location.href="/login";
            }
        )
    }

    render(){
        return(
            <Container component="main" maxWidth="xs" style={{marginTop: "8%"}}>
                <form noValidate onSubmit={this.handleSubmit}>
                    <Grid container spacing={2}>
                        <Typography component="h1" variant="h5">
                            계정 생성
                        </Typography>
                    </Grid>
                    <br></br>
                    <Grid item xs={12}>
                        <TextField 
                            autoComplete="fname"
                            name="username"
                            variant="outlined"
                            required
                            fullWidth
                            id="username"
                            label="사용자 이름"
                            autoFocus
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField 
                            autoComplete="email"
                            name="email"
                            variant="outlined"
                            required
                            fullWidth
                            id="email"
                            label="이메일"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField 
                            autoComplete="current-password"
                            name="password"
                            variant="outlined"
                            required
                            fullWidth
                            id="password"
                            label="비밀번호"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                        >
                            계정 생성
                        </Button>
                    </Grid>
                    <Grid container justifyContent="flex-end">
                        <Grid item>
                            <Link href="/login" variant="body2">
                                이미 계정이 있다면 클릭하여 로그인하세요.
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </Container>
        )
    }
}

export default SignUp;