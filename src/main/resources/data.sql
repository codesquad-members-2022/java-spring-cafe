INSERT INTO `user`(userId, password, name, email) VALUES
('lucid1', 'aaaa', 'cid1', 'lucid1@tesla'),
('lucid2', 'bbbb', 'cid2', 'lucid2@tesla'),
('lucid3', 'cccc', 'cid3', 'lucid3@tesla'),
('lucid4', 'dddd', 'cid4', 'lucid4@tesla'),
('lucid5', 'eeee', 'cid5', 'lucid5@tesla'),
('lucid6', 'ffff', 'cid6', 'lucid6@tesla'),
('lucid7', 'gggg', 'cid7', 'lucid7@tesla'),
('lucid8', 'hhhh', 'cid8', 'lucid8@tesla'),
('lucid9', 'iiii', 'cid9', 'lucid9@tesla');

-- for local
--INSERT INTO article(writer, title, contents, writeTime) VALUES
--('tes1', 'title1', 'content is good!', to_timestamp('03 1월 2022 12:33:55')),
--('tes2', 'title3', 'content is bad,,', to_timestamp('03 1월 2022 06:36:55')),
--('tes3', 'title2', 'content is nice!', to_timestamp('03 2월 2022 12:34:55')),
--('tes4', 'title5', 'content is wonderful!', to_timestamp('03 2월 2022 02:33:55')),
--('tes5', 'title6', 'content is soso,,', to_timestamp('03 3월 2022 06:22:55'));

-- for deploy
INSERT INTO article(writer, title, contents, writeTime) VALUES
('tes1', 'title1', 'content is good!', to_timestamp('03 Jan 2022 12:33:55')),
('tes2', 'title3', 'content is bad,,', to_timestamp('03 Jan 2022 06:36:55')),
('tes3', 'title2', 'content is nice!', to_timestamp('03 Feb 2022 12:34:55')),
('tes4', 'title5', 'content is wonderful!', to_timestamp('03 Feb 2022 02:33:55')),
('tes5', 'title6', 'content is soso,,', to_timestamp('03 Mar 2022 06:22:55'));
