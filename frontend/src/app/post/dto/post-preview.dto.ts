export interface PostPreviewDTO {
  id: string;
  title: string;
  content: string;
  type: PostType;
  file?: string;
  upvotes: number;
  downvotes: number;
  username: string;
  subredditName: string;
  createdAt: string;
}

export enum PostType {
  TEXT = 'TEXT',
  LINK = 'LINK',
  IMAGE = 'IMAGE',
  VIDEO = 'VIDEO'
}
