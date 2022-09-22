import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Button from '../components/Button';

export default function Navbar() {
  const location = useLocation();
  const navigation = useNavigate();

  const [path, setPath] = useState('');

  useEffect(() => {
    setPath(location.pathname);
  }, [location]);

  const postCreateHandler = () => {
    navigation('/posts/create');
  };

  return (
    <>
      {path.includes('/videos') && (
        <nav className="flex sm:text-xs flex-row items-center justify-between">
          <div>
            <Button link="/videos" str="전체" />
            <Button link="/videos/popularity" str="인기 Top 10" />
            <Button link="/videos/training" str="홈트레이닝" />
            <Button link="/videos/stretching" str="스트레칭" />
          </div>
          <div>
            <input placeholder="영상 검색하기" />
          </div>
        </nav>
      )}
      {path.includes('/posts') && (
        <nav className="flex sm:text-xs flex-row items-center justify-between">
          <div>
            <Button link="/posts" str="전체" />
            <Button link="/posts/record" str="운동 기록" />
            <Button link="/posts/meal" str="다이어트 식단" />
            <Button link="/posts/free" str="자유" />
          </div>
          <div>
            <Button
              link="/posts/create"
              str="글쓰기"
              onClick={postCreateHandler}
            />
            <input placeholder="글 검색하기" />
          </div>
        </nav>
      )}
    </>
  );
}
