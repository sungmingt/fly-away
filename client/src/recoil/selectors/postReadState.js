import { selector } from 'recoil';
import instance from '../../service/request';

import { boardIdState } from '../atoms/boardIdState';

export const postReadState = selector({
  key: 'postReadState',
  get: async ({ get }) => {
    try {
      const id = get(boardIdState);
      const res = await instance.get(`/board/${id}`);
      const data = await res.data;
      return data;
    } catch (err) {
      if (err.response.status === 403) {
        alert(err.response.data);
        window.location.replace('/posts');
      }
      console.log('err', err);
    }
  },
});