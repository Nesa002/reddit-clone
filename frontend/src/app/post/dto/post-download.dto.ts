export interface PostDownloadDTO {
  id: string;
  title: string;
  content: string;
  type: PostType;
  file?: string;
  upvotes: number;
  downvotes: number;
  userId: string;
  subredditId: string;
}

export enum PostType {
  TEXT = 'TEXT',
  LINK = 'LINK',
  IMAGE = 'IMAGE',
  VIDEO = 'VIDEO'
}
