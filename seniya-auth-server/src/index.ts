import express, { Request, Response } from 'express';
import cors from 'cors';
import cookieParser from 'cookie-parser';
import jwt, { JwtPayload } from 'jsonwebtoken';

const app = express();
const PORT = 4000;
const SECRET_KEY = 'your-secret-key'; // 실서비스에서는 env로 분리할 것

// ✅ 글로벌 타입 보강 (쿠키 사용 시)
declare global {
    namespace Express {
        interface Request {
            cookies: {
                accessToken?: string;
                [key: string]: any;
            };
        }
    }
}

// ✅ 미들웨어
app.use(cors({
    origin: 'http://localhost:3000',
    credentials: true,
}));
app.use(express.json());
app.use(cookieParser());

// ✅ 로그인 요청 바디 타입
interface LoginRequestBody {
    username: string;
    password: string;
}

// ✅ 로그인
const loginHandler = (req: Request<{}, any, LoginRequestBody>, res: Response): void => {
    const { username, password } = req.body;

    if (username === 'admin' && password === 'admin123') {
        const token = jwt.sign({ username }, SECRET_KEY, { expiresIn: '1h' });

        res.cookie('accessToken', token, {
            httpOnly: true,
            secure: false,
            sameSite: 'lax',
            maxAge: 60 * 60 * 1000,
        });

        res.json({ message: '로그인 성공' });
    } else {
        res.status(401).json({ message: '로그인 실패' });
    }
};

// ✅ 인증 확인
const meHandler = (req: Request, res: Response): void => {
    const token = req.cookies?.accessToken;

    if (!token) {
        res.status(401).json({ message: '토큰 없음' });
        return;
    }

    try {
        const decoded = jwt.verify(token, SECRET_KEY) as JwtPayload;
        res.json({ message: '인증 성공', user: decoded });
    } catch (err) {
        console.error('JWT verify error:', err);
        res.status(401).json({ message: '토큰 유효하지 않음' });
    }
};

// ✅ 로그아웃
const logoutHandler = (_req: Request, res: Response): void => {
    res.clearCookie('accessToken');
    res.json({ message: '로그아웃 완료' });
};

// ✅ 라우팅
app.post('/login', loginHandler);
app.get('/me', meHandler);
app.post('/logout', logoutHandler);

// ✅ 서버 시작
app.listen(PORT, () => {
    console.log(`✅ Auth server is running on http://localhost:${PORT}`);
});
