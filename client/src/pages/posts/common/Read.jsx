import { lazy, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';
import instance from '../../../service/request';
import { memberIdState } from '../../../recoil/atoms/memberIdState';
import { postReadState } from '../../../recoil/selectors/postReadState';
import { commentReadState } from '../../../recoil/selectors/commentReadState';

const Comment = lazy(() => import('./Comment'));

export default function Read() {
  const memberId = useRecoilValue(memberIdState);
  const postRead = useRecoilValue(postReadState);
  const commentRead = useRecoilValue(commentReadState);

  const [commentValue, setCommentValue] = useState('');
  const [modal, setModal] = useState(false);

  const [imgPage, setImgPage] = useState(0);

  const navigation = useNavigate();

  // 수정, 삭제 모달
  const modalHandler = () => {
    setModal((prev) => !prev);
  };

  // 수정하기 버튼
  const editHandler = () => {
    navigation('/posts/edit');
  };

  // 삭제하기 버튼
  const deleteHandler = async () => {
    let item = { memberId: memberId };
    try {
      await instance.delete(
        `/board/${postRead.boardId}`,
        { data: item },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      ),
        alert('해당 글이 삭제되었습니다.');
      window.location.replace('/posts');
    } catch (err) {
      console.log('err', err);
    }
  };

  // 댓글 인풋 값 핸들러
  const commentHandler = (e) => {
    setCommentValue(e.target.value);
  };

  // 댓글 달기 버튼
  const submitHandler = async () => {
    if (commentValue.trim() === '') {
      alert('최소 한 글자 이상 작성해주세요 !');
      return;
    }

    const item = {
      memberId: memberId,
      boardId: postRead.boardId,
      content: commentValue,
    };

    try {
      await instance.post(`/board/${postRead.boardId}/comment`, item, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      alert('댓글이 등록되었습니다 !');
      window.location.replace('/posts/read');
    } catch (err) {
      console.log('err', err);
    }
  };

  const isValid = Number(memberId) === postRead.memberId;

  return (
    <main className="flex flex-col justify-center py-5 px-5 bg-pink">
      <section className="flex justify-between">
        <p className="text-start">{postRead.title}</p>
        {isValid && (
          <div className="flex">
            {modal && (
              <>
                <button onClick={editHandler}>수정</button>
                <button onClick={deleteHandler}>삭제</button>
              </>
            )}
            <button onClick={modalHandler}>&#65049;</button>
          </div>
        )}
      </section>
      <section className="bg-yellow w-full flex justify-center py-5">
        <button
          disabled={imgPage < 1}
          onClick={() => setImgPage((prev) => prev - 1)}
        >
          &#8249;
        </button>
        <img
          className="text-center border-solid border border-zinc-300 p-7 w-9/12 bg-white"
          src={`http://211.41.205.19:8080/board/image/${postRead.imageId[imgPage]}`}
          alt="img"
        />
        <button
          disabled={postRead.imageId.length === imgPage + 1}
          onClick={() => setImgPage((prev) => prev + 1)}
        >
          &#8250;
        </button>
      </section>
      <section className="flex justify-between bg-white">
        <div>
          <span className="mr-4 ml-1">👤 작성자</span>
          <span>{postRead.createdAt}</span>
        </div>
        <button className="mr-1">
          ❤️ {Math.floor(Math.random() * 100) + 1}
        </button>
      </section>
      <p>{postRead.content}</p>
      <span>댓글 {commentRead.length} 개</span>
      <input
        value={commentValue}
        className="w-full"
        placeholder="댓글 작성"
        onChange={commentHandler}
      />
      <div className="flex justify-end border-solid border-b-2">
        <button onClick={submitHandler}>댓글 달기 &rarr;</button>
      </div>
      {commentRead.map((comment) => (
        <Comment key={comment.commentId} items={comment} postRead={postRead} />
      ))}
    </main>
  );
}